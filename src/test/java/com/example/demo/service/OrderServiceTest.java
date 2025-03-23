package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;


import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Product product;
    private Order order;

    @BeforeEach
    void setUp() {
        // Initialize a sample product and order
        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1200.0);

        order = new Order();
        order.setId(1L);
        order.setQuantity(2);
        order.setProduct(product);
    }

    @Test
    public void testCreateOrder_Success() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order createdOrder = orderService.createOrder(1L, 2);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(2, createdOrder.getQuantity());
        assertEquals(product, createdOrder.getProduct());

        // Verify interactions
        verify(productRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testCreateOrder_ProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(1L, 2);
        });

        assertEquals("Product not found", exception.getMessage());

        // Verify interactions
        verify(productRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void testCreateOrder_AddOrderToProduct() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order createdOrder = orderService.createOrder(1L, 2);

        // Assert
        assertNotNull(createdOrder);
        assertTrue(product.getOrders().get(0).getQuantity().equals(createdOrder.getQuantity())); // Verify bidirectional relationship

        // Verify interactions
        verify(productRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
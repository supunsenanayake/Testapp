package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private OrderService userService;

    @Test
    public void createOrder() throws Exception {
        Long productId = 1L;
        Integer quantity = 2;
        Order order = new Order(quantity,new Product());
        Mockito.when(userService.createOrder(productId,quantity)).thenReturn(order);
        mockMvc.perform(post("/orders").param("productId",Long.toString(productId)).param("quantity",Integer.toString(quantity)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").isNotEmpty())
                .andExpect(jsonPath("$.quantity").value(quantity));
    }
}
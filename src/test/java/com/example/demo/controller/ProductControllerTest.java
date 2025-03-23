package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;

//class ProductControllerTest {
//
//}


import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll(); // Clear the database before each test
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Arrange
        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(1200.0);
        product.setDescription("High-performance laptop");

        // Act
        ResultActions result = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Laptop")))
                .andExpect(jsonPath("$.price", is(1200.0)))
                .andExpect(jsonPath("$.description", is("High-performance laptop")));
    }

    @Test
    public void testGetProduct() throws Exception {
        // Arrange
        Product product = new Product();
        product.setName("Smartphone");
        product.setPrice(800.0);
        product.setDescription("Latest model");
        Product savedProduct = productRepository.save(product);

        // Act
        ResultActions result = mockMvc.perform(get("/products/{id}", savedProduct.getId()));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Smartphone")))
                .andExpect(jsonPath("$.price", is(800.0)))
                .andExpect(jsonPath("$.description", is("Latest model")));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Arrange
        Product product = new Product();
        product.setName("Tablet");
        product.setPrice(500.0);
        product.setDescription("Mid-range tablet");
        Product savedProduct = productRepository.save(product);

        Product updatedProduct = new Product();
        updatedProduct.setName("Tablet Pro");
        updatedProduct.setPrice(600.0);
        updatedProduct.setDescription("High-end tablet");

        // Act
        ResultActions result = mockMvc.perform(put("/products/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Tablet Pro")))
                .andExpect(jsonPath("$.price", is(600.0)))
                .andExpect(jsonPath("$.description", is("High-end tablet")));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Arrange
        Product product = new Product();
        product.setName("Monitor");
        product.setPrice(300.0);
        product.setDescription("27-inch monitor");
        Product savedProduct = productRepository.save(product);

        // Act
        ResultActions result = mockMvc.perform(delete("/products/{id}", savedProduct.getId()));

        // Assert
        result.andExpect(status().isNoContent());

        // Verify the product is deleted
        mockMvc.perform(get("/products/{id}", savedProduct.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testListProducts() throws Exception {
        // Arrange
        Product product1 = new Product();
        product1.setName("Keyboard");
        product1.setPrice(50.0);
        product1.setDescription("Mechanical keyboard");

        Product product2 = new Product();
        product2.setName("Mouse");
        product2.setPrice(30.0);
        product2.setDescription("Wireless mouse");

        productRepository.saveAll(List.of(product1, product2));

        // Act
        ResultActions result = mockMvc.perform(get("/products"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Keyboard")))
                .andExpect(jsonPath("$[1].name", is("Mouse")));
    }
}
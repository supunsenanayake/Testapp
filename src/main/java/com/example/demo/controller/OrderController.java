package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        Order order = orderService.createOrder(productId, quantity);
        return ResponseEntity.ok(order);
    }
}

/**
200 OK: Successful GET or PUT request.
201 Created: Successful POST request.
400 Bad Request: Invalid input data.
404 Not Found: Resource not found.
500 Internal Server Error: Server-side error.
* */
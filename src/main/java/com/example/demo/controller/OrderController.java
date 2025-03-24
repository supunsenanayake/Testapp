package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
 * 200 OK: Successful GET or PUT request.
 * 201 Created: Successful POST request.
 * 400 Bad Request: Invalid input data.
 * 404 Not Found: Resource not found.
 * 500 Internal Server Error: Server-side error.
 * <p>
 * 400 Bad Request - This means that client-side input fails validation.
 * 401 Unauthorized - This means the user isn't not authorized to access a resource. It usually returns when the user isn't authenticated.
 * 403 Forbidden - This means the user is authenticated, but it's not allowed to access a resource.
 * 404 Not Found - This indicates that a resource is not found.
 * 500 Internal server error - This is a generic server error. It probably shouldn't be thrown explicitly.
 * 502 Bad Gateway - This indicates an invalid response from an upstream server.
 * 503 Service Unavailable - This indicates that something unexpected happened on server side (It can be anything like server overload, some parts of the system failed, etc.).
 */
package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name must not be blank")
    private String name;

    @NotNull(message = "Price must not be null")
    @Min(value = 1, message = "Price must be greater than zero")
    private Double price;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    // Helper method to add an order
    public void addOrder(Order order) {
        orders.add(order);
        order.setProduct(this);
    }

    // Helper method to remove an order
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setProduct(null);
    }
}

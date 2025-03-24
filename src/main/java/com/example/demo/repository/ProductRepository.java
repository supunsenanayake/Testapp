package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
public List<Product> getProductByName(String name);
    @Query("FROM Product")
    List<Product> findAllProducts();

    @Query("SELECT p.name, p.price FROM Product p")
    List<Object[]> findProductNamesAndPrices();

    @Query("FROM Product p WHERE p.price > :minPrice")
    List<Product> findProductsByPriceGreaterThan(@Param("minPrice") double minPrice);

    @Query("FROM Product p WHERE p.name = :name")
    List<Product> findProductsByName(@Param("name") String name);

    @Query("FROM Product p ORDER BY p.price DESC")
    List<Product> findAllProductsOrderByPriceDesc();

    @Query("FROM Product p ORDER BY p.name")
    List<Product> findProductsWithPagination(Pageable pageable);
}

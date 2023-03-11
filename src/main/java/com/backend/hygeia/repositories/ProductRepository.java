package com.backend.hygeia.repositories;

import java.util.List;

import com.backend.hygeia.entities.Product;

public interface ProductRepository {
    List<Product> findAll();
    Product findById(Long id);
    Product save(Product product);
    void deleteById(Long id);
}

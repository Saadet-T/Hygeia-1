package com.backend.hygeia.repositories;

import java.util.List;

import com.backend.hygeia.entities.Category;

public interface CategoryRepository {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
}

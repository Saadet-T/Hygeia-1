package com.backend.hygeia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.hygeia.entities.Category;
import com.backend.hygeia.repositories.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;
	
    public List<Category> getAllCategories() {
    	List<Category> categoryList = categoryRepository.findAll();

        return categoryList;
    }
}

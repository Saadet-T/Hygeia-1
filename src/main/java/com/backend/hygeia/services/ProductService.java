package com.backend.hygeia.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.hygeia.entities.Product;
import com.backend.hygeia.repositories.ProductRepository;


@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	
    public List<Product> getAllProducts() {
    	List<Product> productList = productRepository.findAll();

        return productList;
    }
    
}

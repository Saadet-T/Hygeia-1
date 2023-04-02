package com.backend.hygeia.services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.hygeia.entities.Product;
import com.backend.hygeia.repositories.ProductRepository;
import com.google.gson.Gson;


@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	
	Gson gson = new Gson();
	
    public List<Product> getAllProducts() {
    	List<Product> productList = productRepository.findAll();

        return productList;
    }
    public Map<Product,String> getAllProductsWithJson() {
    	List<Product> productList = productRepository.findAll();
        Map<Product,String> productWithJSONList = new HashMap<>();
        for (Product product : productList) {
        	
        	productWithJSONList.put(product, gson.toJson(product));
    	}
        return productWithJSONList;
    }

    
}

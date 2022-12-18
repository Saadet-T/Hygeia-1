package com.backend.hygeia.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.backend.hygeia.entities.Product;
import com.backend.hygeia.repositories.ProductRepository;

import java.util.*;

@Controller
public class HomePageController {
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping
	String getProducts(Model model) {
		List<Product> productList = new ArrayList<Product>();
		
		Optional<Product> possibleProduct = productRepository.findByName("Patates");
		Product product = new Product(Long.valueOf(1), "name", "description","type");
		
		if(possibleProduct.isPresent())
		{
			product = possibleProduct.get();
		}
		model.addAttribute("attiributeName",product.getName());
		model.addAttribute("products");

		return "index";
	}
}

package com.backend.hygeia.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.backend.hygeia.entities.Category;
import com.backend.hygeia.entities.Product;
import com.backend.hygeia.repositories.ProductRepository;
import com.backend.hygeia.services.CategoryService;
import com.backend.hygeia.services.ProductService;

import java.util.*;

@Controller
public class HomePageController {

	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/")
	String getProducts(Model model) {
        List<Product> productList = productService.getAllProducts();
        List<Category> categoryList = categoryService.getAllCategories();
        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", categoryList);
        //Return edilen isim sayfanın ismidir index.html ye götürür buraya gelen istekleri
        return "index";
	}
	@GetMapping("/login")
	String getProduct() {
        return "login";
	}
	@GetMapping("/OpenRedirectForm")
	String getProducts() {
        return "OpenRedirectForm";
	}
	@GetMapping("/signup")
	String getProduc() {
        return "signup";
	}
	@GetMapping("/OpenRedirect")
	String getProdu() {
        return "OpenRedirect";
	}
}

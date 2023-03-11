package com.backend.hygeia.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.backend.hygeia.entities.Product;
import com.backend.hygeia.repositories.ProductRepository;
import com.backend.hygeia.services.ProductService;

import java.util.*;

@Controller
public class HomePageController {

	@Autowired
	ProductService productService;
	
	@GetMapping("/")
	String getProducts(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("productList", productList);
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

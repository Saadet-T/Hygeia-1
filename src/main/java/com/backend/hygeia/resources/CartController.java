package com.backend.hygeia.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.backend.hygeia.entities.UserProduct;
import com.backend.hygeia.services.UserProductService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	UserProductService userProductService;
	
	@PostMapping("/updateCart")
	public UserProduct addToCart(@RequestBody UserProduct userProduct) {
		UserProduct up = userProductService.saveToDatabase(userProduct);
		return up;
	}
	
	@PostMapping("/removeFromCart")
	public void removeFromCart(@RequestBody Long userProductId) {
		
		userProductService.deleteById(userProductId);
	}
	


}

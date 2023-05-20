package com.backend.hygeia.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.hygeia.entities.UserProduct;
import com.backend.hygeia.services.UserProductService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	UserProductService userProductService;
	
	@PostMapping("/addToCart")
	public void getLogin(@RequestBody UserProduct userProduct) {
		
		userProductService.saveToDatabase(userProduct);
	}


}

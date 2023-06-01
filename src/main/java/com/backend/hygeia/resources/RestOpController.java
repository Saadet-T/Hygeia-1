package com.backend.hygeia.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.backend.hygeia.repositories.UserRepository;

@RestController
public class RestOpController {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/delete_user/{id}")
	public void deleteUser(@PathVariable Long id ){
		userRepository.delete(userRepository.findById(id).get());
	}
}

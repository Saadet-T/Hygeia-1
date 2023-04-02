package com.backend.hygeia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.backend.hygeia.entities.User;
import com.backend.hygeia.entities.UserProduct;
import com.backend.hygeia.repositories.UserProductRepository;
import com.backend.hygeia.repositories.UserRepository;
import com.backend.hygeia.security.services.UserDetailsImpl;

@Service
public class UserProductService {
	@Autowired
	UserProductRepository userProductRepository;
	
	@Autowired
	UserRepository userRepository;
	
    public List<UserProduct> getAllUserProducts() {
    	List<UserProduct> userProductList = userProductRepository.findAll();
    	
        return userProductList;
    }
    
    public UserProduct saveToDatabase(UserProduct userProduct) {
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
         Optional<User> optionalUser = userRepository.findById(userId);
         if(optionalUser.isPresent())
         {
        	 userProduct.setUser(optionalUser.get()); 
         }
    	
        return userProductRepository.save(userProduct);
    }
}

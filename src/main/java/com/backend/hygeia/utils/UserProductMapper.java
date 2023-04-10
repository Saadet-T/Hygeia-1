package com.backend.hygeia.utils;

import java.sql.ResultSet;
import java.util.Optional;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.backend.hygeia.entities.User;
import com.backend.hygeia.entities.UserProduct;
import com.backend.hygeia.repositories.ProductRepository;
import com.backend.hygeia.repositories.UserRepository;

@Component
public class UserProductMapper implements RowMapper<UserProduct> {

	private static final Logger logger = LogManager.getLogger(UserProductMapper.class);
	//TODO User ve Product nesnelerini doldurmak için veri tabanına gidiliyor Bu işlemin alternatifi düşünülebilir.
	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public UserProduct mapRow(ResultSet rs, int rowNum) {
		UserProduct userProduct = new UserProduct();
		try {
			Optional<User> optionalUser = userRepository.findById(rs.getLong("user_id"));
			if (optionalUser.isPresent()) {
				userProduct.setUser(optionalUser.get());
			} else {
				logger.error("UserProduct tablosundaki user_id degerine karsilik bir id degeri bulunamadı");
			}
			
			userProduct.setId(rs.getLong("id"));
			userProduct.setStatus(rs.getInt("status"));
			userProduct.setProduct(productRepository.findById(rs.getLong("product_id")));

			userProduct.setQuantity(rs.getInt("quantity"));
		} catch (Exception e) {
			
			logger.error("When try to map UserProduct from the sql an error accured: {}", e);
		}

		return userProduct;
	}
}

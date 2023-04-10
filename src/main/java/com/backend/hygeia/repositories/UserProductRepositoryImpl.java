package com.backend.hygeia.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.backend.hygeia.entities.UserProduct;
import com.backend.hygeia.utils.UserProductMapper;

@Repository
public class UserProductRepositoryImpl implements UserProductRepository {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserProductMapper userProductMapper;

	public UserProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<UserProduct> findAll() {
		String sql = "SELECT * FROM user_products";
		return jdbcTemplate.query(sql, userProductMapper);
	}

	@Override
	public UserProduct findById(Long id) {
		String sql = "SELECT * FROM user_products WHERE id = ?";
		Object[] args = { id };
		List<UserProduct> userProducts = jdbcTemplate.query(sql, args, userProductMapper);
		if (userProducts.isEmpty()) {
			return null;
		}
		return userProducts.get(0);
	}

	@Override
	public UserProduct save(UserProduct userProduct) {
		if (userProduct.getId() == null) {
			return insert(userProduct);
		}
		return update(userProduct);
	}

	@Override
	public void deleteById(Long id) {
		String sql = "DELETE FROM user_products WHERE id = ?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);

	}

	private UserProduct insert(UserProduct userProduct) {
		String id="userProduct.getProduct().getQuantity()";
		String sql = "INSERT INTO user_products( status, product_id, user_id, quantity) VALUES ( ?,?, ?, ?) RETURNING id";
		
		Object[] args = { userProduct.getStatus(), userProduct.getProduct().getId(), userProduct.getUser().getId(), userProduct.getQuantity() };
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, userProduct.getStatus());
			ps.setLong(2, userProduct.getProduct().getId());
			ps.setLong(3, userProduct.getUser().getId());
			ps.setInt(4, userProduct.getQuantity());
			return ps;
		}, keyHolder);
		userProduct.setId(keyHolder.getKey().longValue());
		return userProduct;
	}

	private UserProduct update(UserProduct userProduct) {
		String sql = "UPDATE user_products SET status = ?, product_id = ?, user_id = ?, quantity = ? WHERE id = ?";
		Object[] args = { userProduct.getStatus(), userProduct.getProduct().getId(), userProduct.getUser().getId(), userProduct.getQuantity(), userProduct.getId() };
		jdbcTemplate.update(sql, args);
		return userProduct;
	}

}

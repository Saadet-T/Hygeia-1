package com.backend.hygeia.utils;

import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import com.backend.hygeia.entities.Category;


public class CategoryMapper implements RowMapper<Category> {

	private static final Logger logger = Logger.getLogger(CategoryMapper.class);

	@Override
	public Category mapRow(ResultSet rs, int rowNum) {
		Category category = new Category();
		try {
			category.setId(rs.getLong("id"));
			category.setName(rs.getString("name"));
			category.setDescription(rs.getString("description"));
			category.setStatus(rs.getInt("status"));
		} catch (Exception e) {
			logger.error("When try to map Category from the sql an error accured: {}", e);
		}

		return category;
	}
}

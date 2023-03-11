package com.backend.hygeia.utils;

import org.springframework.jdbc.core.RowMapper;

import com.backend.hygeia.entities.Category;
import com.backend.hygeia.entities.Product;

import java.sql.ResultSet;
import java.sql.SQLException;



public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        double price = rs.getDouble("price");
        String imgPath = rs.getString("img_path");
        int status = rs.getInt("product_status");
        
        Long categoryId = rs.getLong("category_id");
        String categoryName = rs.getString("category_name");
        String categoryDescription = rs.getString("category_description");
        int categoryStatus = rs.getInt("category_status");
        
        Category category = new Category();
        category.setId(categoryId);
        category.setDescription(categoryDescription);
        category.setName(categoryName);
        category.setStatus(categoryStatus);

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setPrice(price);
        product.setImgPath(imgPath);
        product.setStatus(status);

        return product;
    }
}
package com.backend.hygeia.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.backend.hygeia.entities.Product;
import com.backend.hygeia.utils.ProductMapper;


@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT p.id, p.name, p.description, p.category_id, c.name as category_name, c.description as category_description, p.price, p.img_path, p.status as product_status, c.status as category_status FROM products p INNER JOIN categories c ON p.category_id = c.id ORDER BY p.category_id;";
        return jdbcTemplate.query(sql, new ProductMapper());
    }

    @Override
    public Product findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Object[] args = {id};
        List<Product> products = jdbcTemplate.query(sql, args, new ProductMapper());
        if (products.isEmpty()) {
            return null;
        }
        return products.get(0);
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            return insert(product);
        }
        return update(product);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        Object[] args = {id};
        jdbcTemplate.update(sql, args);
    }

    private Product insert(Product product) {
        String sql = "INSERT INTO products (name, description, categoryId, price, imgPath, status) VALUES (?, ?, ?, ?, ?, ?)";
        Object[] args = {product.getName(), product.getDescription(), product.getCategory().getId(), product.getPrice(), product.getImgPath(), product.getStatus()};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setLong(3, product.getCategory().getId());
            ps.setDouble(4, product.getPrice());
            ps.setString(5, product.getImgPath());
            ps.setInt(6, product.getStatus());
            return ps;
        }, keyHolder);
        product.setId(keyHolder.getKey().longValue());
        return product;
    }

    private Product update(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, categoryId = ?, price = ?, imgPath = ?, status = ? WHERE id = ?";
        Object[] args = {product.getName(), product.getDescription(), product.getCategory().getId(), product.getPrice(), product.getImgPath(), product.getStatus(), product.getId()};
        jdbcTemplate.update(sql, args);
        return product;
    }
}
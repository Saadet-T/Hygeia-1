package com.backend.hygeia.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.backend.hygeia.entities.Category;
import com.backend.hygeia.utils.CategoryMapper;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM category";
        return jdbcTemplate.query(sql, new CategoryMapper());
    }

    @Override
    public Category findById(Long id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        Object[] args = {id};
        List<Category> categories = jdbcTemplate.query(sql, args, new CategoryMapper());
        if (categories.isEmpty()) {
            return null;
        }
        return categories.get(0);
    }

    @Override
    public Category save(Category category) {
        if (category.getId() == null) {
            return insert(category);
        }
        return update(category);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM category WHERE id = ?";
        Object[] args = {id};
        jdbcTemplate.update(sql, args);
    }

    private Category insert(Category category) {
        String sql = "INSERT INTO category (name, description, status) VALUES (?, ?, ?)";
        Object[] args = {category.getName(), category.getDescription(), category.getStatus()};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getStatus());
            return ps;
        }, keyHolder);
        category.setId(keyHolder.getKey().longValue());
        return category;
    }

    private Category update(Category category) {
        String sql = "UPDATE category SET name = ?, description = ?, status = ? WHERE id = ?";
        Object[] args = {category.getName(), category.getDescription(), category.getStatus(), category.getId()};
        jdbcTemplate.update(sql, args);
        return category;
    }

}

package com.backend.hygeia.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.backend.hygeia.entities.Notice;
import com.backend.hygeia.security.jwt.AuthTokenFilter;
import com.backend.hygeia.utils.NoticeMapper;
import com.google.gson.Gson;

@Repository
public class NoticeRepositoryImpl implements NoticeRepository{

	private final JdbcTemplate jdbcTemplate;
	

	
	
	public NoticeRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Notice> findAll() {
		String sql = "SELECT * FROM notices";
        return jdbcTemplate.query(sql, new NoticeMapper());
	}

	@Override
	public Notice findById(Long id) {
		String sql = "SELECT * FROM notices WHERE id = ?";
        Object[] args = {id};
        List<Notice> notices = jdbcTemplate.query(sql, args, new NoticeMapper());
        if (notices.isEmpty()) {
            return null;
        }
        return notices.get(0);
	}

	@Override
	public Notice save(Notice notice) {
        if (notice.getId() == null) {
            return insert(notice);
        }
        return update(notice);
	}

	@Override
	public void deleteById(Long id) {
        String sql = "DELETE FROM notices WHERE id = ?";
        Object[] args = {id};
        jdbcTemplate.update(sql, args);
		
	}
	
    private Notice insert(Notice notice) {
        String sql = "INSERT INTO notices (name, description, status, img_path) VALUES (?, ?, ?, ?)";
        Object[] args = {notice.getName(), notice.getDescription(), notice.getStatus(), notice.getImgPath()};
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, notice.getName());
            ps.setString(2, notice.getDescription());
            ps.setInt(3, notice.getStatus());
            ps.setString(4, notice.getImgPath());
            return ps;
        }, keyHolder);
        notice.setId(keyHolder.getKey().longValue());
        return notice;
    }
    
    private Notice update(Notice notice) {
        String sql = "UPDATE notices SET name = ?, description = ?, status = ? WHERE id = ?";
        Object[] args = {notice.getName(), notice.getDescription(), notice.getStatus(), notice.getId()};
        jdbcTemplate.update(sql, args);
        return notice;
    }

}

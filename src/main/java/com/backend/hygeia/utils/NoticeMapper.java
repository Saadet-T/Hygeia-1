package com.backend.hygeia.utils;

import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.backend.hygeia.entities.Notice;

public class NoticeMapper implements RowMapper<Notice>{
	
	private static final Logger logger = LogManager.getLogger(NoticeMapper.class);

	@Override
	public Notice mapRow(ResultSet rs, int rowNum) {
		Notice notice = new Notice();
		try {
			notice.setId(rs.getLong("id"));
			notice.setName(rs.getString("name"));
			notice.setDescription(rs.getString("description"));
			notice.setStatus(rs.getInt("status"));
			notice.setImgPath(rs.getString("img_path"));
			notice.setFirstLine(rs.getString("first_line"));
			notice.setSecondLine(rs.getString("second_line"));
			notice.setThirdLine(rs.getString("third_line"));
			
		} catch (Exception e) {
			logger.error("When try to map Category from the sql an error accured: {}", e);
		}

		return notice;
	}

}

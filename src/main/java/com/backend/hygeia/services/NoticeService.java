package com.backend.hygeia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.hygeia.entities.Notice;
import com.backend.hygeia.repositories.NoticeRepository;

@Service
public class NoticeService {
	@Autowired
	NoticeRepository noticeRepository;
	
    public List<Notice> getAllNotices() {
    	List<Notice> noticeList = noticeRepository.findAll();

        return noticeList;
    }
    
    public Notice save(Notice notice) {
    	noticeRepository.findAll();

    	
    	
    	return noticeRepository.save(notice);
    }
}

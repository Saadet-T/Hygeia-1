package com.backend.hygeia.repositories;

import java.util.List;

import com.backend.hygeia.entities.Notice;

public interface NoticeRepository {
    List<Notice> findAll();
    Notice findById(Long id);
    Notice save(Notice notice);
    void deleteById(Long id);
}

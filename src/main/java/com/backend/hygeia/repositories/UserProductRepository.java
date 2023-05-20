package com.backend.hygeia.repositories;

import java.util.List;

import com.backend.hygeia.entities.UserProduct;

public interface UserProductRepository {

    List<UserProduct> findAll();
    UserProduct findById(Long id);
    List<UserProduct> findAllByUserId(Long UserId);
    UserProduct save(UserProduct userProduct);
    void deleteById(Long id);
}

package com.backend.hygeia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.hygeia.entities.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  
	Optional<Product> findByName(String name);

  Boolean existsByName(String name);

}
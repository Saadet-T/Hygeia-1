package com.backend.hygeia.repositories.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.hygeia.entities.address.Neighborhood;

public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Long>{

	  Optional<Neighborhood> findByName(String name);
	  
	  Optional<Neighborhood> findById(Long id);
}

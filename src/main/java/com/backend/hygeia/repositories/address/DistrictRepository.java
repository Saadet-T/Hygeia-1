package com.backend.hygeia.repositories.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.hygeia.entities.address.District;

public interface DistrictRepository  extends JpaRepository<District, Long>{
	
	  Optional<District> findByName(String name);
	  
	  Optional<District> findById(Long id);

}

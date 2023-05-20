package com.backend.hygeia.repositories.address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.hygeia.entities.address.City;

public interface CityRepository extends JpaRepository<City, Long>{
	
	  Optional<City> findByName(String name);
	  
	  Optional<City> findById(Long id);

}

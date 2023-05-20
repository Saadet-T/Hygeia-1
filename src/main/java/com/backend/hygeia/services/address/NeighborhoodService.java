package com.backend.hygeia.services.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.hygeia.entities.address.Neighborhood;
import com.backend.hygeia.repositories.address.NeighborhoodRepository;

@Service
public class NeighborhoodService {
	
    @Autowired
    private NeighborhoodRepository neighborhoodRepository;

    public List<Neighborhood> getAllNeighborhood() {
        return neighborhoodRepository.findAll();
    }

    // Diğer metotlar...

}

package com.backend.hygeia.services.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.hygeia.entities.address.District;
import com.backend.hygeia.repositories.address.DistrictRepository;

@Service
public class DistrictService {
	
	@Autowired
	DistrictRepository districtRepository;
	
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

}

package com.backend.hygeia.services.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.hygeia.entities.address.City;
import com.backend.hygeia.repositories.address.CityRepository;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    // Diğer metotlar...
}
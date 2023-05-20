package com.backend.hygeia.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.backend.hygeia.entities.address.City;
import com.backend.hygeia.entities.address.District;
import com.backend.hygeia.entities.address.Neighborhood;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "neighborhood_id", referencedColumnName = "id")
    private Neighborhood neighborhood;
	
	@NotBlank
	@Size(max = 1000)
	@JsonProperty("Sokak")
	private String street;
	
	@NotBlank
	@Size(max = 1000)
	@JsonProperty("Numara")
	private String number;

	@NotBlank
	@Size(max = 1000)
	@JsonProperty("Tarif")
	private String directions;
	
	@Size(max = 1000)
	@JsonProperty("durum")
	private int status;
}

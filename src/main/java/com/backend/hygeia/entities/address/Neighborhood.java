package com.backend.hygeia.entities.address;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "neighborhoods")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Neighborhood {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 100)
	private String name;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    private District district;
}

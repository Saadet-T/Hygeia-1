package com.backend.hygeia.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notices", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 100)
	private String name;

	@NotBlank
	@Size(max = 1000)
	private String description;
	
	@Size(max = 1000)
	private int status;
	
	@Size(max = 1000)
	private String imgPath;
	
	@NotBlank
	@Size(max = 1000)
	private String firstLine;
	
	@NotBlank
	@Size(max = 1000)
	private String secondLine;
	
	@NotBlank
	@Size(max = 1000)
	private String thirdLine;
}

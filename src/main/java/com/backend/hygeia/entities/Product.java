package com.backend.hygeia.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "name") 
    })
public class Product {
	
	public Product(Long id, @NotBlank @Size(max = 100) String name,
			@NotBlank @Size(max = 1000) @Email String description, @NotBlank @Size(max = 1000) String type) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.type = type;
	}

	public Product() {
		super();
	}
	
	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;

	  @NotBlank
	  @Size(max = 100)
	  private String name;

	  @NotBlank
	  @Size(max = 1000)
	  @Email
	  private String description;

	  @NotBlank
	  @Size(max = 1000)
	  private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	  
	
	  
	  
}

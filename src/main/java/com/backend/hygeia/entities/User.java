package com.backend.hygeia.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
	
	
 







public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password, @NotBlank @Size(max = 120) String returnURL,@NotBlank @Size(max = 120) String number,@NotBlank @Size(max = 120) String address,@NotBlank @Size(max = 120) String postCode) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.returnURL = returnURL;
		this.number=number;
		this.address=address;
		this.postCode=postCode;
	}

@Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;
  
  @NotBlank
  @Size(max = 120)
  private
  String postCode;
  
  @NotBlank
  @Size(max = 120) 
  private
  String address;
  
  @NotBlank
  @Size(max = 120) 
  private
  String number;
  
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;
  
  @NotBlank
  @Size(max = 120)
  private String returnURL;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
  
  
}

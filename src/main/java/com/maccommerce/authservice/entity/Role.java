package com.maccommerce.authservice.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	public long getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	private String role;
	
	@ManyToMany(cascade = {CascadeType.ALL},  
				mappedBy = "roles")
	private Set<DAOUser> users = new HashSet<>();

	public Role(String role) {
		super();
		this.role = role;
	}
	
	public Role() {
		super();
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}


	
}

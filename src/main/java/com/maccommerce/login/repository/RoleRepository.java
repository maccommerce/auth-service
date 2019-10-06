package com.maccommerce.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maccommerce.login.bean.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
}




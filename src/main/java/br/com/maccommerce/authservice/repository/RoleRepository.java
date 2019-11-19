package br.com.maccommerce.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.maccommerce.authservice.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}




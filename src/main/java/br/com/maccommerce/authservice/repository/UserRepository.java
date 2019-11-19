package br.com.maccommerce.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.maccommerce.authservice.entity.DAOUser;

@Repository
public interface UserRepository extends JpaRepository<DAOUser, Integer> {
	
	DAOUser findByUsername(String username);
}
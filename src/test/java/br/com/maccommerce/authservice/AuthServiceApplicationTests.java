package br.com.maccommerce.authservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.maccommerce.authservice.entity.DAOUser;
import br.com.maccommerce.authservice.entity.Role;
import br.com.maccommerce.authservice.repository.RoleRepository;
import br.com.maccommerce.authservice.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Set<Role> createRoleUser(){
		Optional<Role> role = roleRepository.findById(1001);
		Set<Role> roles = new HashSet<Role>();
		roles.add(role.get());
		return roles;
	}
	
	@Test
	public void testRoles() {
		
		List<Role> roles = roleRepository.findAll();
		
		List<Role> rolesValidas = new ArrayList<Role>();;
		
		Role roleAdm = new Role(1001, "ADMINISTRADOR");
		rolesValidas.add(roleAdm);
		Role roleVend = new Role(1002, "VENDEDOR");
		rolesValidas.add(roleVend);
		Role roleClient = new Role(1003, "CLIENTE");
		rolesValidas.add(roleClient);
		
		assertThat(roles.size()).isEqualTo(3);
		
		assertThat(roles.get(0).getId()).isEqualTo(rolesValidas.get(0).getId());
		assertThat(roles.get(0).getRole()).isEqualTo(rolesValidas.get(0).getRole());
		
		assertThat(roles.get(1).getId()).isEqualTo(rolesValidas.get(1).getId());
		assertThat(roles.get(1).getRole()).isEqualTo(rolesValidas.get(1).getRole());
		
		assertThat(roles.get(2).getId()).isEqualTo(rolesValidas.get(2).getId());
		assertThat(roles.get(2).getRole()).isEqualTo(rolesValidas.get(2).getRole());

	}
	
	@Test
	public void registryShouldPersistData() {
				
		DAOUser user = new DAOUser("userTest", "passTest", createRoleUser());
		
		this.userRepository.save(user);
		
		assertThat(user.getId()).isNotNull();
	}
	

	@Test
	public void deleteShouldRemoveData() {
		
		DAOUser user = new DAOUser("userTest1", "passTest", createRoleUser());
		
		this.userRepository.save(user);
		this.userRepository.delete(user);
		
		assertThat(userRepository.findById(user.getId())).isEmpty();
		
	}
	
	@Test
	public void updateShouldChangeAndPersistData() {
		
		DAOUser user = new DAOUser("userTest2", "passTest", createRoleUser());
		
		this.userRepository.save(user);
		
		user.setUsername("newName");
		user.setPassword("newPass");
		
		DAOUser userChanged = this.userRepository.save(user);
		
		assertThat(userChanged.getUsername()).isEqualTo("newName");
		assertThat(userChanged.getPassword()).isEqualTo("newPass");
	
		
	}
}

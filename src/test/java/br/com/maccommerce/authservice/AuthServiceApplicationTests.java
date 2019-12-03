package br.com.maccommerce.authservice;

import br.com.maccommerce.authservice.entity.User;
import br.com.maccommerce.authservice.entity.Role;
import br.com.maccommerce.authservice.repository.RoleRepository;
import br.com.maccommerce.authservice.repository.UserRepository;
import org.flywaydb.core.Flyway;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@FlywayTest
@SpringBootTest
@RunWith(SpringRunner.class)
class AuthServiceApplicationTests {

	@Autowired private Flyway flyway;

	@Autowired private RoleRepository roleRepository;
	
	@Autowired private UserRepository userRepository;

	private static DatabaseMock databaseMock;

	@BeforeAll static void beforeAll() throws IOException {
		databaseMock = new DatabaseMock();
	}

	@AfterAll static void afterAkk() throws IOException {
		databaseMock.stopServer();
	}

	@BeforeEach void beforeEach() {
		flyway.clean();
		flyway.migrate();
	}

	public Set<Role> createRoleUser(){
		Role role = roleRepository.getOne(1001);
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		return roles;
	}
	
	@Test public void testRoles() {
		
		List<Role> roles = roleRepository.findAll();
		
		List<Role> rolesValidas = new ArrayList<>();
		
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
	
	@Test public void registryShouldPersistData() {
				
		User user = new User("userTest", "passTest", createRoleUser());
		
		this.userRepository.save(user);
		
		assertThat(user.getId()).isNotNull();
	}

	@Test public void deleteShouldRemoveData() {
		
		User user = new User("userTest1", "passTest", createRoleUser());
		
		this.userRepository.save(user);
		this.userRepository.delete(user);
		
		assertThat(userRepository.findById(user.getId())).isEmpty();
		
	}
	
	@Test public void updateShouldChangeAndPersistData() {
		
		User user = new User("userTest2", "passTest", createRoleUser());
		
		this.userRepository.save(user);
		
		user.setUsername("newName");
		user.setPassword("newPass");
		
		User userChanged = this.userRepository.save(user);
		
		assertThat(userChanged.getUsername()).isEqualTo("newName");
		assertThat(userChanged.getPassword()).isEqualTo("newPass");

	}

}

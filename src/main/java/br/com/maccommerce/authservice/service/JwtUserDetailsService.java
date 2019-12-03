package br.com.maccommerce.authservice.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.maccommerce.authservice.entity.User;
import br.com.maccommerce.authservice.entity.Role;
import br.com.maccommerce.authservice.entity.UserDTO;
import br.com.maccommerce.authservice.repository.RoleRepository;
import br.com.maccommerce.authservice.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
	
	public User save(UserDTO user, Integer idRole) throws Exception {
		if(userRepository.findByUsername(user.getUsername()) != null) {
			throw new Exception("Login já existe");
		}
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		Optional<Role> role = roleRepository.findById(idRole);
		if(!role.isPresent()) {
			throw new RoleNotFoundException("id-" + idRole);
		}
		Set<Role> roles = new HashSet<Role>();
		roles.add(role.get());
		newUser.setRoles(roles);
		
	
		return userRepository.save(newUser);
	}
	
	
	public String loadUserRolesByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Usuario não encontrado: " + username);
		}
		
		Set<Role> roles = user.getRoles();
		Role it=null;
		 Iterator<Role> rolesAsIterator = roles.iterator();
         while (rolesAsIterator.hasNext()){
                it = rolesAsIterator.next();
                 
         }
  		return it.getId()+"-"+it.getRole();
	}
}
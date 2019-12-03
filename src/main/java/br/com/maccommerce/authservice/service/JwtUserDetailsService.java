package br.com.maccommerce.authservice.service;

import br.com.maccommerce.authservice.entity.Role;
import br.com.maccommerce.authservice.entity.User;
import br.com.maccommerce.authservice.entity.UserDTO;
import br.com.maccommerce.authservice.repository.RoleRepository;
import br.com.maccommerce.authservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.*;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder bcryptEncoder;

	public JwtUserDetailsService(
			UserRepository userRepository,
			RoleRepository roleRepository,
			PasswordEncoder bcryptEncoder
	) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bcryptEncoder = bcryptEncoder;
	}

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
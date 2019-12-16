package br.com.maccommerce.authservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.maccommerce.authservice.entity.JwtRequest;
import br.com.maccommerce.authservice.entity.JwtResponse;
import br.com.maccommerce.authservice.entity.UserDTO;
import br.com.maccommerce.authservice.jwt.config.JwtTokenUtil;
import br.com.maccommerce.authservice.service.JwtUserDetailsService;

@RestController
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	Logger logger =  LoggerFactory.getLogger(this.getClass());

	@RequestMapping({ "/token-validate" })
	public String tokenValidate() {
		return "OK";
	}
			
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
				
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new JwtResponse(token, 
				userDetailsService.loadUserRolesByUsername(authenticationRequest.getUsername())));
		}
	
	@RequestMapping(value = "/register/{idRole}", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user, @PathVariable Integer idRole) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user, idRole));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException e) {
			throw new Exception("Credencial inválida", e);
		}
	}
}
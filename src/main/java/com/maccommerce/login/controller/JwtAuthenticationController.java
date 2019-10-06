package com.maccommerce.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maccommerce.login.bean.DAOUser;
import com.maccommerce.login.bean.JwtRequest;
import com.maccommerce.login.bean.JwtResponse;
import com.maccommerce.login.bean.UserDTO;
import com.maccommerce.login.jwt.config.JwtTokenUtil;
import com.maccommerce.login.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	



	@RequestMapping({ "/token-validate" })
	public String tokenValidate() {
		return "OK";
	}
			
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok()
        .header("token",token)
        .body(userDetailsService
				.loadUserRolesByUsername(authenticationRequest.getUsername()));
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
package br.com.maccommerce.authservice.controller;

import br.com.maccommerce.authservice.config.jwt.JwtTokenUtil;
import br.com.maccommerce.authservice.entity.JwtRequest;
import br.com.maccommerce.authservice.entity.UserDTO;
import br.com.maccommerce.authservice.service.JwtUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class JwtAuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final JwtUserDetailsService userDetailsService;

	public JwtAuthenticationController(
			AuthenticationManager authenticationManager,
			JwtTokenUtil jwtTokenUtil,
			JwtUserDetailsService userDetailsService
	) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
	}

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
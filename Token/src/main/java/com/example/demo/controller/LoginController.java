package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtService;
import com.example.demo.entity.JwtRequest;
import com.example.demo.entity.JwtResponse;


@RestController
@RequestMapping("/api/v1")
public class LoginController {

	@Autowired
	private JwtService jwtService;

	// auth manager is used to the authenticate based on password like
	// if won't add this user can login with email and any password to avoide that
	// we have to add the
	// authmanage
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	public JwtResponse createToken(@RequestBody JwtRequest request) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		if (authentication.isAuthenticated()) {
			String token = jwtService.genarateToken(request.getEmail());
			JwtResponse response = new JwtResponse();
			response.setToken(token);
			return response;
		} else {
			throw new UsernameNotFoundException("invalid user");
		}

	}

}

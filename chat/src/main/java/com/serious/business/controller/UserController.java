package com.serious.business.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.serious.business.domain.UserDTO;
import com.serious.business.security.SecurityContext;
import com.serious.business.service.UserService;

/**
 * Exposes some endpoints to access {@link com.serious.business.domain.UserDTO}.
 * @author Pavel F.
 * @since 1.0
 * */
@RestController
public class UserController {

	private UserService userService;
	private SecurityContext securityContext;
	
	@Autowired
	public UserController(UserService userService,
			SecurityContext securityContext) {
		this.userService = userService;
		this.securityContext = securityContext;
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, 
			path = "/profile/{id}")	
	public ResponseEntity<UserDTO> getUser() {
		return null;
	}
	
	@GetMapping(produces = { MediaType.TEXT_HTML_VALUE }, 
			path = "/")	
	public ResponseEntity<UserDTO> test() {
		
		System.err.println("METHOD");
		this.securityContext.getUsername();
		return null;
	}
	
}

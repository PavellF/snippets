package com.serious.business.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serious.business.domain.Role;
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
	
	private boolean isSuper() {
		return securityContext.hasRole(Role.SUPERUSER);
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, 
			path = "/user/{ids}")	
	public ResponseEntity<List<UserDTO>> getUsers(@PathVariable("ids") String ids) {
		
		Stream<Integer> profileIds = Pattern.compile("-")
				.splitAsStream(ids)
				.map(Integer::valueOf);
		
		if (securityContext.isAuthorized()) {
			
			if (isSuper()) {
				return ResponseEntity.ok(userService
						.fetchUsersById(profileIds, true));
			}
			
			//only superusers can request multiple users
			if (profileIds.count() != 1) {
				return ResponseEntity.badRequest().build();
			}
			
			Optional<String> requestedID = profileIds.reduce((a, b) -> a + b)
					.map(String::valueOf);
			
			Optional<String> userId = securityContext.getId(); 
			
			if (requestedID.equals(userId)) {
				return ResponseEntity.ok(userService
						.fetchUsersById(profileIds, false));
			}
			
		} 
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping(path = "/user", 
			produces = { MediaType.APPLICATION_JSON_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> updateUser(HttpEntity<UserDTO> entity) {
		
		UserDTO user = entity.getBody();
		
		if (securityContext.isAuthorized() && user != null) {
			
			Boolean areEqual = securityContext.getId().map(Integer::valueOf)
			.map(id -> id.equals(user.getId())).orElseGet(() -> Boolean.FALSE);;
			
			if (areEqual || isSuper()) {
				userService.updateUser(user);
				return ResponseEntity.noContent().build();
			}
			
		} 
		
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping(path = "/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
		
		if (securityContext.isAuthorized()) {
			
			Boolean areEqual = securityContext.getId().map(Integer::valueOf)
					.map(reqId -> id == reqId).orElseGet(() -> Boolean.FALSE);
			
			if (areEqual || isSuper()) {
				userService.deleteUserById(id);
				return ResponseEntity.noContent().build();
			}
			
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping(path = "/user", 
			produces = { MediaType.APPLICATION_JSON_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserDTO> createUser(UserDTO user) {
		
		if (user == null) {
			return ResponseEntity.badRequest().build();
		}
		
		int id = userService.createUser(user);
		return ResponseEntity.created(URI.create("/user/" + id)).build();
	}
	
	@GetMapping(produces = { MediaType.TEXT_HTML_VALUE }, 
			path = "/")	
	public ResponseEntity<UserDTO> test() {
		
		System.err.println("METHOD");
		this.securityContext.getUsername();
		return null;
	}
	
}

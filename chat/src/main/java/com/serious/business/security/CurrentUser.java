package com.serious.business.security;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.serious.business.domain.Role;
import com.serious.business.repository.UserRepository;
import com.serious.business.repository.domain.User;

/**
 * Basic implementation for {@link SecurityContext} with basic authorization.
 * @author Pavel F.
 * @since 1.0
 * */
@Component
@RequestScope
@Lazy(true)
public class CurrentUser implements SecurityContext {

	private final Optional<String> username;
	private final Optional<Role> role;
	private final Optional<String> id;
	
	private Optional<User> fetchUser(HttpServletRequest request,
				UserRepository repository) {
		
		if (request == null) {
			return Optional.empty();
		}
		
		String authorization = request.getHeader("Authorization");
		
		if (authorization == null) {
			return Optional.empty();
		}
		
		String[] emailPasswordPair = authorization.split("@");
		String email = emailPasswordPair[0];
		String password = emailPasswordPair[1];
		
		if (emailPasswordPair.length != 2) {
			return Optional.empty();
		}
		
		return repository.fetchByEmail(email).filter((user) -> {
			return password.equals(new String(user.getPassword()));
		});
	}
	
	@Autowired
	public CurrentUser(HttpServletRequest request, UserRepository repository) {
		System.err.println("INITIALIZED");
		User user = fetchUser(request, repository).orElse(User.EMPTY);
		
		this.username = Optional.ofNullable(user.getEmail());
		this.role = Optional.ofNullable(user.getRole());
		
		Integer id = user.getId();
		
		this.id = Optional.ofNullable(id != null ? id.toString() : null);
	}

	public boolean isAuthorized() {
		return username.isPresent() || id.isPresent();
	}

	
	public Optional<String> getUsername() {
		return username;
	}

	public boolean hasRole(final Role toCompare) {
		return role.map(role -> role.equals(toCompare)).orElse(Boolean.FALSE);
	}

	public boolean isSuper() {
		return hasRole(Role.SUPERUSER);
	}
	
	public Optional<String> getId() {
		return id;
	}
	
}

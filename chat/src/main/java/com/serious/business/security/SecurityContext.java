package com.serious.business.security;

import java.util.Optional;

import com.serious.business.domain.Role;

/**
 * Provides minimum information about user made request. Immutable.
 * @author Pavel F.
 * @since 1.0
 * */
public interface SecurityContext {

	/**
	 * Returns true if there is associated user name or id with with request.
	 * */
	boolean isAuthorized();
	
	/**
	 * If user is anonymous always empty.
	 * */
	Optional<String> getUsername();
	
	/**
	 * Case sensitive!
	 * */
	boolean hasRole(final Role toCompare);
	
	/**
	 * If user is anonymous always empty.
	 * */
	Optional<String> getId();
}

package com.serious.business.security;

import java.util.Optional;

/**
 * Provides minimum information about user made request. Immutable.
 * @author Pavel F.
 * @since 1.0
 * */
public interface SecurityContext {

	/**
	 * Returns true if there is no associated user name or id with with request.
	 * */
	boolean isAnonymous();
	
	/**
	 * If user is anonymous always empty.
	 * */
	Optional<String> getUsername();
	
	/**
	 * Case sensitive!
	 * */
	boolean hasRole(final String toCompare);
	
	/**
	 * If user is anonymous always empty.
	 * */
	Optional<String> getId();
}

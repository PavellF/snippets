package security;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import controllers.UserPageController;
import objects.User;

public class SecurityUtilities {

	private static final Logger logger = LogManager.getLogger(SecurityUtilities.class.getName());
	
	public static ThreadPrincipalContainer getThreadPrincipal(Authentication authentication){
		
		Authentication auth = (null == authentication) ? 
				SecurityContextHolder.getContext().getAuthentication() : authentication;
		
		if(auth != null  && !(auth instanceof AnonymousAuthenticationToken)){
			
			ThreadPrincipalContainer tpc = (ThreadPrincipalContainer) auth.getPrincipal();
			
			if(tpc != null){
				logger.info("Authentication is not null. Bounded to current thread ThreadPrincipalContainer is returning..");
				return tpc;
			}else{
				logger.info("Authentication is not null. Bounded to current thread ThreadPrincipalContainer is null. Null is returning..");
				return null;
			}
		}
		logger.info("Authentication is null or annonymous.");
		return null;
	}
	
	public static User getPrincipalUser(){
		
		final ThreadPrincipalContainer tpc = getThreadPrincipal(null);
		
		if(tpc == null){
			logger.error("Can not get thread principal. Null is returning..");
			return null;
		}
		
		return tpc.getUser();
	}
	
	public static boolean isPrincipal(long id){
		
		ThreadPrincipalContainer tpc = getThreadPrincipal(null);
		
		if(tpc == null) return false;
		
		return tpc.getUser().getId() == id;
	}
}

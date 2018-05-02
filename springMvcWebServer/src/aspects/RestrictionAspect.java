package aspects;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import controllers.RegisterPageController;
import exceptions.AccessDeniedException;

@Aspect
public class RestrictionAspect {
	
	private static final Logger logger = LogManager.getLogger(RestrictionAspect.class.getName());

	@Pointcut("execution(** persistence.UsersRepository.delete(..))")
	public void deleteUser(){}
	
	@Pointcut("execution(** persistence.FriendsRepository.addToFriends(..))")
	public void addToFriends(){}
	
	@Pointcut("execution(** persistence.FriendsRepository.deleteFromFriends(..))")
	public void deleteFromFriends(){}
	
	@Pointcut("execution(** persistence.FriendsRepository.acceptFriend(..))")
	public void acceptFriend(){}
	
	@Around("deleteUser()")
	public void checkAuthorityForDeleteUser(ProceedingJoinPoint jp){
		if(hasAuthority(RestrictionAspectConstants.DELETE_USER_ATHORITIES)){
			try {
				jp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}else throw new AccessDeniedException("You have no authority to do it");
		logger.info("Proceed method.");
	}
	/*
	@Around("addToFriends()")
	public void checkAuthorityForAddToFriends(ProceedingJoinPoint jp){
		if(hasAuthority(RestrictionAspectConstants.ADD_FRIEND_ATHORITIES)){
			try {
				jp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}else throw new AccessDeniedException("You have no authority to do it");
		logger.info("Proceed method.");
	}
	
	@Around("deleteFromFriends()")
	public void checkAuthorityForDeleteFromFriends(ProceedingJoinPoint jp){
		if(hasAuthority(RestrictionAspectConstants.DELETE_FRIEND_ATHORITIES)){
			try {
				jp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}else throw new AccessDeniedException("You have no authority to do it");
		logger.info("Proceed method.");
	}
	
	@Around("acceptFriend()")
	public void checkAuthorityForAcceptFriend(ProceedingJoinPoint jp){
		if(hasAuthority(RestrictionAspectConstants.ACCEPT_FRIEND_ATHORITIES)){
			try {
				jp.proceed();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}else throw new AccessDeniedException("You have no authority to do it");
		logger.info("Proceed method.");
	}
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private boolean hasAuthority(String... allowed){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null){
			logger.info("Authentication is not null.");
			
			List<? extends GrantedAuthority> list = (List<? extends GrantedAuthority>) auth.getAuthorities();
			
			final int size = list.size();
			
			for(int i = 0; i<size; i++){
				for(String authority : allowed){
					if(list.get(i).getAuthority().equals(authority)) return true;
				}
			}
		}
		logger.info("Authentication is null. Going to return false...");
		return false;
	}
	
}

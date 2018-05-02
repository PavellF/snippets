package controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.google.gson.Gson;

import exceptions.AccessDeniedException;
import exceptions.AuthenticationProblemException;
import exceptions.InternalServerException;
import exceptions.InvalidRequestParameterException;
import exceptions.NonexistentDomainException;
import exceptions.UserDoesNotExistException;
import internationalization.InternationalizationService;
import internationalization.Locale;
import objects.User;
import security.SecurityUtilities;
import util.HTTPUtils;

@ControllerAdvice
public class ApplicationExceptionHandler {

	private static final Logger logger = LogManager.getLogger(ApplicationExceptionHandler.class.getName());
	
	@Autowired 
	private InternationalizationService internationalizationService;
	
	@Autowired 
	private Gson gson;
	
	@ExceptionHandler(AccessDeniedException.class)
	public String handleAccessDeniedException(
			HttpServletRequest request,
			Model model,
			AccessDeniedException ex){
		
		Locale locale = getLocale(request);
		
		model.addAttribute("type","Access denied");
		model.addAttribute("code",ex.getCode());
		model.addAttribute("message",ex.getMessage());
		
		logger.error("An AccessDeniedException has been thrown. Redirecting to error page..");
		
		model.addAllAttributes(internationalizationService.getAllFileContent(Aliases.FOOTER_AND_HEADER, locale));
		
		return "error";
	}
	
	@ExceptionHandler(UserDoesNotExistException.class)
	public String handleUserDoesNotExistException(
			HttpServletRequest request,
			Model model,
			UserDoesNotExistException ex){
		
		Locale locale = getLocale(request);
		
		model.addAttribute("type","Object does not exist.");
		model.addAttribute("code",ex.getCode());
		model.addAttribute("message",ex.getMessage());
		
		logger.error("An UserDoesNotExistException has been thrown. Redirecting to error page..");
		
		model.addAllAttributes(internationalizationService.getAllFileContent(Aliases.FOOTER_AND_HEADER, locale));
		
		return "error";
	}
	
	
	@ExceptionHandler(InvalidRequestParameterException.class)
	public ResponseEntity<String> handleInvalidRequestParameterException(
			InvalidRequestParameterException ex){
		logger.error("An InvalidRequestParameterException has been thrown. Returning an error json..");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json; charset=UTF-8");
		
		return new ResponseEntity<String>(
						gson.toJson(ex),
						headers,
						HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AuthenticationProblemException.class)
	public ResponseEntity<String> handleAuthenticationProblemException(
			AuthenticationProblemException ex){
		logger.error("An AuthenticationProblemException has been thrown. Returning an error json..");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json; charset=UTF-8");
		
		return new ResponseEntity<String>(
						gson.toJson(ex),
						headers,
						HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NonexistentDomainException.class)
	public ResponseEntity<String> handleNonexistentDomainException(NonexistentDomainException ex){
		logger.error("A NonexistentDomainException has been thrown. Returning an error json..");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json; charset=UTF-8");
		
		return new ResponseEntity<String>(
						gson.toJson(ex),
						headers,
						HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<String> handleInternalServerException(InternalServerException ex){
		logger.error("An InternalServerException has been thrown. Returning an error json..");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json; charset=UTF-8");
		
		return new ResponseEntity<String>(
						gson.toJson(ex),
						headers,
						HttpStatus.BAD_GATEWAY);
	}
	
	
	private Locale getLocale(HttpServletRequest request){
		
		final Cookie cookie = WebUtils.getCookie(request, "locale");
		
		Locale locale = null;
		User principalUser = SecurityUtilities.getPrincipalUser();
		
		if(principalUser != null){
			
			locale = principalUser.getLocale();
		}else{
			
			locale = (cookie != null) ? 
					(Locale) HTTPUtils.parseEnum(cookie.getValue(), Locale.values()) : Locale.EN;
			
		}
		
		return locale;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

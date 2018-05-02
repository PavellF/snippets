package controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import exceptions.AccessDeniedException;

@ControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler(AccessDeniedException.class)
	public String ModelAndView(Model model){
		model.addAttribute("message", "Access is Denied.");
		return "errorpage";
	}
	
}

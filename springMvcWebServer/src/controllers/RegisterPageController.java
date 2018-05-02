package controllers;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import email.EmailService;
import internationalization.InternationalizationService;
import objects.SignUpForm;
import persistence.UsersRepository;
import services.UserService;
import services.UsersInfoService;

@Controller
public class RegisterPageController {

	private static final Logger logger = LogManager.getLogger(RegisterPageController.class.getName());
	/*
	@Autowired 
	private InternationalizationService internationalizationService;
	
	@Autowired 
	private UsersInfoService usersInfoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value="/signup",method=RequestMethod.GET)
	public String showForm(@RequestParam(value = "error", required = false) String error, Model model){
		
		final String viewName = "signuppage";
		final String language = LocaleContextHolder.getLocale().getLanguage();
		
		if(error != null) {
		switch(error){
		case "validate":internationalizationService.internationalizeElement(model, "pageErrors", language, "signuperror");break;
		case "duplicate":internationalizationService.internationalizeElement(model, "pageErrors", language, "duplicateerror");break;
		}
		}
		
		logger.info("Country language is: ".concat(language));
		
		internationalizationService.internationalizePage(model, viewName, language);
		internationalizationService.internationalizePage(model, "footerAndHeader", language);
		
		return viewName;
	}
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public String recieveForm(@Valid SignUpForm form, Errors errors, RedirectAttributes data) throws MessagingException{
		
		if(errors.hasErrors()){
			logger.info("Received a wrong data from request. Redirecting to /signup?error=validate");
			return "redirect:/signup?error=validate";
		}
		
		if(userService.save(form) == -1){
			logger.info("Can not write duplicate values into db. Redirecting to /signup?error=duplicate");
			return "redirect:/signup?error=duplicate";
		}
		
		data.addAttribute("username", form.getUsername());
		data.addFlashAttribute("user", form);
		
		usersInfoService.addNewLastRegistered(form.getUsername());
		
		emailService.sendSignUpSuccessEmail(form, LocaleContextHolder.getLocale().getLanguage());
		
		logger.info("Success. Redirecting to /users/".concat(form.getUsername()));
		
		return "redirect:/users/{username}";
	}
	*/
}

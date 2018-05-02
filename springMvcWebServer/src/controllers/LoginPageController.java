package controllers;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import internationalization.InternationalizationFileImp;
import internationalization.InternationalizationService;
import internationalization.Locale;
import objects.LoginForm;
import objects.User;
import security.SecurityUtilities;
import security.ThreadPrincipalContainer;
import services.ForgotPasswordService;
import services.UsersInfoService;
import util.HTTPUtils;

@Controller
public class LoginPageController {
	
	@Autowired 
	private UsersInfoService usersInfoService;
	
	@Autowired 
	private InternationalizationService internationalizationService;
	
	private static final Logger logger = LogManager.getLogger(LoginPageController.class.getName());

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginPage(
			@RequestParam(value = "error", required = false) String error,
			@CookieValue(value="country", required=false) String countryCookie,
			RedirectAttributes data,
			Model model){
		
		Locale locale = null;
		User principalUser = SecurityUtilities.getPrincipalUser();
		
		if(principalUser != null){
			
			logger.info("Already logged in user requested login page. Redirecting to profile page.");
			data.addAttribute("username", principalUser.getUsername());
			return "redirect:/users/{username}";
			
		}else{
			
			locale = (countryCookie != null) ? 
					(Locale) HTTPUtils.parseEnum(countryCookie, Locale.values()) : Locale.EN;
			
		}
		
		logger.info("User's countryCookie value is "+countryCookie);
		
		Map<String,String> modelMap = internationalizationService.mergeAndGetAllFileContent
				(locale, Aliases.LOGIN_PAGE, Aliases.FOOTER_AND_HEADER);
		
		if (error != null) {
			modelMap.put("loginerror",
				(String) internationalizationService.getFileEntry(Aliases.APP_ERRORS, locale, "loginerror", String.class));
		}
		
		model.addAllAttributes(modelMap);
		
		return Aliases.LOGIN_PAGE;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@Valid LoginForm form, Errors errors, RedirectAttributes data ){
		Authentication auth;
		
		if(errors.hasErrors()) {
			logger.info("Received a wrong data from request. Redirecting to /login?error=true");
			SecurityContextHolder.getContext().setAuthentication(null);
			return "redirect:/login?error=true";
		}
		
		auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) usersInfoService.addNewLastLoggedIn(auth.getName());
		
		data.addAttribute("username", SecurityUtilities.getPrincipalUser().getUsername());
		
		return "redirect:/users/{username}";
	}
	
	
	
}

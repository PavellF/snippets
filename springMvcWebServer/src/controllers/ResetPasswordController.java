package controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import internationalization.InternationalizationService;
import services.ForgotPasswordService;
import services.ForgotPasswordServiceImp;

@Controller
public class ResetPasswordController {
	
	private static final Logger logger = LogManager.getLogger(ResetPasswordController.class.getName());
/*
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@Autowired 
	private InternationalizationService internationalizationService;


	@RequestMapping(value="/resetWord",method=RequestMethod.POST)
	public String forgotPassword(HttpServletRequest request){
		String email = request.getParameter("email");
		logger.info("Reset password was requested. Email is ".concat(email));
		forgotPasswordService.sendEmail(email,request.getRequestURL().toString(),LocaleContextHolder.getLocale().getLanguage());
		return "redirect:/login";
	}
	
	@RequestMapping(value="/resetWord/{id}",method=RequestMethod.GET)
	public String resetPassword(@PathVariable String id,@RequestParam(value = "error", required = false) String error, Model model){
		
		final String viewName = "resetPasswordPage";
		final String language = LocaleContextHolder.getLocale().getLanguage();
		
		model.addAttribute("postURL", id);
		
		logger.info("Country language is: ".concat(language));
		
		if(error != null){
			logger.error("Get page with error: "+error);
			switch(error){
			case "length":internationalizationService.internationalizeElement(model, "pageErrors", language, "passwordLengthError");break;
			case "fail":internationalizationService.internationalizeElement(model, "pageErrors", language, "resetPassword");break;
			}
		}
		
		internationalizationService.internationalizePage(model, viewName, language);
		internationalizationService.internationalizePage(model, "footerAndHeader", language);
		
		return viewName;
	}
	
	@RequestMapping(value="/resetWord/{id}",method=RequestMethod.POST)
	public String doReset(@PathVariable String id, HttpServletRequest request){
		
		String newPassword = request.getParameter("password");
		final int length = newPassword.length();
		
		if(length < 4 || length > 36 || newPassword == null){
			return "redirect:/resetWord/"+id+"?error=length";
		}
		
		if(!forgotPasswordService.updateAccountByEmailId(id,newPassword)){
			return "redirect:/resetWord/"+id+"?error=fail";
		}
		
		return "redirect:/login";
	}*/
}

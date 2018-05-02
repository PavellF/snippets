package controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HTTPErrorsController {

	@RequestMapping(value = "/errors", method = RequestMethod.GET)
	   public String renderErrorPage(HttpServletRequest httpRequest, Model model) {
	         
	        String errorMessage = "";
	        final int httpErrorCode = getErrorCode(httpRequest);
	        
	        switch (httpErrorCode) {
	            case 400: {
	                errorMessage = "HTTP Error Code: 400. Bad Request";
	                break;
	            }
	            case 401: {
	                errorMessage = "HTTP Error Code: 401. Unauthorized";
	                break;
	            }
	            case 404: {
	                errorMessage = "HTTP Error Code: 404. Resource not found";
	                break;
	            }
	            case 500: {
	                errorMessage = "HTTP Error Code: 500. Internal Server Error";
	                break;
	            }
	        }
	        
	        model.addAttribute("message", errorMessage);
	        return "errorpage";
	    }
	     
	    private int getErrorCode(HttpServletRequest httpRequest) {
	        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
	    }
}

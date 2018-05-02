package controllers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import config.DatabaseConfig;
import exceptions.AccessDeniedException;
import util.Properties;

@Controller
public class MainPageController {

	private boolean serverSide = false;
	private static final Logger logger = LogManager.getLogger(DatabaseConfig.class.getName());

	
	@RequestMapping(value={"/","/main"}, method=RequestMethod.GET)
	public String calcPage(Model model){
		
		if(serverSide == false){
			logger.info("RETURNING USERSIDE MAIN PAGE");
			
			return "calcpageUserSide";
		}else{
			
			logger.info("RETURNING STANDARD MAIN PAGE");
			
			model.addAttribute("brokerLogin", Properties.get().getString("MESSAGE_BROKER_LOGIN"));
			model.addAttribute("brokerPassword", Properties.get().getString("MESSAGE_BROKER_PASSWORD"));
			model.addAttribute("heartbeatUserOut", Properties.get().getString("USER_HEARTBEAT_SEND_INTERVAL"));
			model.addAttribute("heartbeatUserIn", Properties.get().getString("USER_HEARTBEAT_RECEIVE_INTERVAL"));
			
			return "calcpage";
		}
	}
	
	@RequestMapping(value="/stsocsc", method=RequestMethod.GET)
	public String switchToServerOrClientSideComputing(){
		
		SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach((GrantedAuthority ga) -> {
			if(!ga.getAuthority().equals("root")) {
				logger.info("USER DOES NOT HAVE THE ROOT AUTHORITY TO PERFORM THIS ACTION");
				throw new AccessDeniedException();
			}
		});
		
		if(serverSide == false) {
			serverSide = true;
			logger.info("FROM NOW SERVER PERFORMS ALL COMPUTING");
		}else {
			serverSide = false;
			logger.info("FROM NOW USER PERFORMS ALL COMPUTING");
		}
		
		return "redirect:/main";
	}
	
	
}

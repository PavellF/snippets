package messaging;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import persistence.UsersRepository;

@Controller
public class SignUpValidatorController {
	
	private static final Logger logger = Logger.getLogger(SignUpValidatorController.class);
	
	@Autowired
	private UsersRepository userRepository;

	@MessageMapping("/meessaging/checkUsername")
	@SendToUser(destinations="/queue/checkUsername",broadcast=false)
	public String checkUsername(String incoming){
		
		/*if(incoming == null) throw new IllegalStateException("NULL received.");
		
		logger.info("Received: ".concat(incoming));
		
		if(userRepository.doesUserExist(incoming)){
			logger.info("User exists. Returning true..");
			return "true";
		}
		
		logger.info("User does not exist. Returning false..");
		*/
		return "false";
	}
	
	@MessageMapping("/meessaging/checkEmail")
	@SendToUser(destinations="/queue/checkEmail",broadcast=false)
	public String checkEmail(String incoming){
/*
		if(incoming == null) throw new IllegalStateException("NULL received.");
		
		logger.info("Received: ".concat(incoming));
		
		if(userRepository.doesEmailExist(incoming)){
			logger.info("User with this email exists. Returning true..");
			return "true";
		}
		
		logger.info("User with this email does not exist. Returning false..");
		*/
		return "false";
	}
}

package services;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import controllers.LoginPageController;
import email.EmailService;
import objects.User;
import persistence.UsersRepository;
import util.AppUtilities;

@Component
public class ForgotPasswordServiceImp implements ForgotPasswordService{

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UsersRepository usersRepository;
	
	private final Map< String,String> idEmailPair = new HashMap<>(); 
	
	private static final Logger logger = LogManager.getLogger(ForgotPasswordServiceImp.class.getName());
	
	@Override
	public void sendEmail(String email,String url,String country) {
		/*String id =  AppUtilities.generateRandomStringValue(6);
		idEmailPair.put( id,email);
		logger.info("Email has been received and its id is ".concat(id));
		try {
			emailService.sendResetPasswordEmail(email, url,id, country);
		} catch (MessagingException e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public boolean updateAccountByEmailId(String id,String newPassword) {
		
		if(!idEmailPair.containsKey(id)){
			logger.info("Can not find email with this id. False is returning..");
			return false;
		}
		
		String email = idEmailPair.get(id);
		
		User user = usersRepository.findByEmail(email);
		
		if(user == null){
			logger.info("DB does not contain an entry with that email. False is returning..");
			return false;
		}
		
		user.setPassword(new StandardPasswordEncoder().encode(newPassword));
		
		usersRepository.update(user);
		
		idEmailPair.remove(id);
		
		logger.info("User's password has been updated.");
		
		return true;
	}
	

}

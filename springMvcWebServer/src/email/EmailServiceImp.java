package email;

import java.util.Calendar;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import controllers.Aliases;
import internationalization.InternationalizationService;
import internationalization.Locale;
import objects.SignUpForm;

@Component
public class EmailServiceImp implements EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired 
	private InternationalizationService internationalizationService;
	
	@Autowired
	private ServletContext sc;
	
	private static final String FROM = "Application";
	
	private static final Logger logger = LogManager.getLogger(EmailServiceImp.class.getName());
	
	@Override
	public boolean sendSignUpSuccessEmail(SignUpForm form, Locale locale) throws MessagingException {
		
		try{
		
			Map<String, String> emailContent = internationalizationService.getAllFileContent(Aliases.SIGN_UP_EMAIL, locale);
				
			emailContent.put("formName",form.getUsername());
			emailContent.put("formPassword",form.getPassword());
			emailContent.put("currentDate",Calendar.getInstance().getTime().toString());
		
			final String content = EmailLoader.get().getTemplate(sc.getRealPath("WEB-INF/email/signUp.html") , emailContent);
		
			MimeMessage mime = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mime, false, "UTF-8");
		
			helper.setTo(form.getEmail());
			helper.setFrom(FROM);
			helper.setSubject("Registration success.");
			helper.setText(content, true);
			mailSender.send(mime);
			logger.info("Sign up email was sended to ".concat(form.getEmail()));
		
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean sendSimpleTextEmail(String to, String from, String message,String subject)  throws MessagingException{
		
		try{
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			
			logger.info("Sending email to "+to);
			
			mailMessage.setFrom(from);
			mailMessage.setTo(to);
			mailMessage.setSubject(subject);
			mailMessage.setText(message);
			mailSender.send(mailMessage);
			
			logger.info("Email to "+to+" has been sended.");
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean sendResetPasswordEmail(String to, String url, Locale locale) throws MessagingException {
		
		try{
		
			Map<String, String> emailContent = internationalizationService.getAllFileContent(Aliases.RESET_PASSWORD_EMAIL, locale);
		
			emailContent.put("currentDate", Calendar.getInstance().getTime().toString());
			emailContent.put("url", url);
		
			final String content = EmailLoader.get().getTemplate(sc.getRealPath("WEB-INF/email/resetPassword.html") , emailContent);
		
			MimeMessage mime = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mime, false, "UTF-8");
		
			helper.setTo(to);
			helper.setFrom(FROM);
			helper.setSubject("Reset password.");
			helper.setText(content, true);
			mailSender.send(mime);
		
			logger.info("Reset password email was sended to ".concat(to));
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

}

package email;

import javax.mail.MessagingException;

import internationalization.Locale;
import objects.SignUpForm;

public interface EmailService {

	public boolean sendSignUpSuccessEmail(SignUpForm form, Locale locale) throws javax.mail.MessagingException;
	
	public boolean sendResetPasswordEmail(String to, String url, Locale locale) throws javax.mail.MessagingException;
	
	public boolean sendSimpleTextEmail(String to, String from, String message,String subject)  throws MessagingException;
}

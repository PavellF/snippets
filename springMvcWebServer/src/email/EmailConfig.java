package email;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class EmailConfig {

	@Bean(name="mailSender")
	public JavaMailSender mailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(EmailConstants.EMAIL_HOST);
		mailSender.setPort(EmailConstants.EMAIL_PORT);
		mailSender.setUsername(EmailConstants.EMAIL_USERNAME);
		mailSender.setPassword(EmailConstants.EMAIL_PASSWORD);
		mailSender.setDefaultEncoding(EmailConstants.EMAIL_DEFAULT_ENCODING);
		mailSender.setJavaMailProperties(getProperties());
		return mailSender;
	}
	
	private Properties getProperties(){
		final Properties props = new Properties();
		props.put("mail.smtp.socketFactory.port", EmailConstants.SMTP_SOCKET_FACTORY_PORT);
		props.put("mail.smtp.socketFactory.class",EmailConstants.SMTP_SOCKET_FACTORY_CLASS);
		props.put("mail.smtp.auth", EmailConstants.SMTP_AUTH);
		props.put("mail.smtp.port", EmailConstants.EMAIL_PORT);
		return props;
	}
}

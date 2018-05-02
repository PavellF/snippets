package internationalization;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controllers.Aliases;

@Configuration
public class InternationalizationConfig {

	@Autowired
	private ServletContext sc;
	
	@Bean
	public InternationalizationService internationalizationService(){
		Map<String, String> pathAliasPairs = new HashMap<>();
		
		pathAliasPairs.put(sc.getRealPath("WEB-INF/internationalization/loginpage.ru"), Aliases.LOGIN_PAGE+Locale.RU);
		pathAliasPairs.put(sc.getRealPath("WEB-INF/internationalization/pageErrors.ru"), Aliases.APP_ERRORS+Locale.RU);
		pathAliasPairs.put(sc.getRealPath("WEB-INF/internationalization/footerAndHeader.ru"), Aliases.FOOTER_AND_HEADER+Locale.RU);
		pathAliasPairs.put(sc.getRealPath("WEB-INF/internationalization/signuppage.ru"), "signuppage"+Locale.RU);
		pathAliasPairs.put(sc.getRealPath("WEB-INF/internationalization/resetPasswordPage.ru"), "resetPasswordPage"+Locale.RU);
		pathAliasPairs.put(sc.getRealPath("WEB-INF/internationalization/usersProfile.ru"), Aliases.USER_PROFILE_PAGE+Locale.RU);
		pathAliasPairs.put(sc.getRealPath("WEB-INF/internationalization/messagesBlock.ru"), Aliases.MESSAGES_BLOCK+Locale.RU);
		
		pathAliasPairs.put(sc.getRealPath("WEB-INF/internationalization/signUpEmail.ru"), Aliases.SIGN_UP_EMAIL+Locale.RU);
		pathAliasPairs.put(sc.getRealPath("WEB-INF/internationalization/resetPasswordEmail.ru"), Aliases.RESET_PASSWORD_EMAIL+Locale.RU);
		
		pathAliasPairs.put(sc.getRealPath("WEB-INF/internationalization/notificationContent.ru"), Aliases.NOTIFICATION_CONTENT+Locale.RU);
		
		return new InternationalizationServiceImp(pathAliasPairs, Locale.RU);
	}
}

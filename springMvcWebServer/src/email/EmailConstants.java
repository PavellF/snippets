package email;



import util.Properties;

public class EmailConstants {

	public static final String EMAIL_HOST = Properties.get().getString("EMAIL_HOST");
	public static final String EMAIL_USERNAME = Properties.get().getString("EMAIL_USERNAME");
	public static final String EMAIL_PASSWORD = Properties.get().getString("EMAIL_PASSWORD");
	public static final int EMAIL_PORT = Properties.get().getInt("EMAIL_PORT");
	public static final String EMAIL_DEFAULT_ENCODING = Properties.get().getString("EMAIL_DEFAULT_ENCODING");
	
	public static final String SMTP_SOCKET_FACTORY_PORT = Properties.get().getString("SMTP_SOCKET_FACTORY_PORT");
	public static final String SMTP_SOCKET_FACTORY_CLASS = Properties.get().getString("SMTP_SOCKET_FACTORY_CLASS");
	public static final String SMTP_AUTH = Properties.get().getString("SMTP_AUTH");
	public static final String SMTP_PORT = Properties.get().getString("SMTP_PORT");
	
	
}

package theChainOfResponsibility;

public class LoginForm {

	private String login;
	private String password;
	private boolean rememberMe;
	
	public LoginForm(String login, String password, boolean rememberMe) {
		super();
		this.login = login;
		this.password = password;
		this.rememberMe = rememberMe;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String toString() {
		return "LoginForm [login=" + login + ", password=" + password + ", rememberMe=" + rememberMe + "]";
	}
	
}

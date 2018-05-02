package objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {

	@Size(min=2,max=36)
	@NotNull
	private String username;
	
	@Size(min=4,max=36)
	@NotNull
	private String password;
	
	@NotNull
	private boolean rememberme;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberme;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberme = rememberMe;
	}
}

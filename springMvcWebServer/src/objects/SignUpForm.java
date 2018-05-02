package objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class SignUpForm {

	@Size(min=2,max=36)
	@NotNull
	private String username;
	
	@Size(min=4,max=36)
	@NotNull
	private String password;
	
	@Size(min=6,max=36)
	@NotNull
	@Email
	private String email;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

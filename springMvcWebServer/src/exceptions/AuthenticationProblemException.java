package exceptions;

import com.google.gson.annotations.Expose;

@SuppressWarnings("serial")
public class AuthenticationProblemException extends RuntimeException{

	@Expose
	final int code = 5;
	
	@Expose
	private String message;

	public AuthenticationProblemException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}

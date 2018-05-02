package exceptions;

import com.google.gson.annotations.Expose;

@SuppressWarnings("serial")
public class NonexistentDomainException extends RuntimeException{

	@Expose
	final int code = 6;
	
	@Expose
	private String message;
	
	public NonexistentDomainException(String message) {
		super();
		this.message = message;
	}

	public NonexistentDomainException(){}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

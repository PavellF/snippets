package exceptions;

import com.google.gson.annotations.Expose;

@SuppressWarnings("serial")
public class InternalServerException extends RuntimeException{

	@Expose
	private String message;
	
	@Expose
	private final int code = 7;
	
	public InternalServerException(String message){
		this.message = message;
	}
	
	public InternalServerException(){}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}
}

package exceptions;

import com.google.gson.annotations.Expose;

@SuppressWarnings("serial")
public class InvalidRequestParameterException extends RuntimeException{

	@Expose
	private String message;
	
	@Expose
	private final int code = 3;
	
	public InvalidRequestParameterException(){
		
	}
	
	public InvalidRequestParameterException(String message){
		this.message = message;
	}

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

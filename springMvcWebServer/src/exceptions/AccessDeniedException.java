package exceptions;

import com.google.gson.annotations.Expose;

@SuppressWarnings("serial")
public class AccessDeniedException extends RuntimeException{

	@Expose
	private String message;
	
	@Expose
	private final int code = 0;
	
	public AccessDeniedException(String message){
		this.message = message;
	}

	public AccessDeniedException(){}
	
	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}
}

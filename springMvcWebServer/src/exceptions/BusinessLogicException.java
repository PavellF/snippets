package exceptions;

@SuppressWarnings("serial")
public class BusinessLogicException extends RuntimeException{

	private String message;
	
	private final int code = 1;
	
	public BusinessLogicException(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	

}

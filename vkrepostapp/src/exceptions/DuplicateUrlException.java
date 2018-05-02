package exceptions;

@SuppressWarnings("serial")
public class DuplicateUrlException extends RuntimeException{
	

	private String message;
	
	public DuplicateUrlException(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}

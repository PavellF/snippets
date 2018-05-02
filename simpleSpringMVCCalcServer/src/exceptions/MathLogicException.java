package exceptions;

@SuppressWarnings("serial")
public class MathLogicException extends RuntimeException{

	private String message;
	
	public  MathLogicException(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}

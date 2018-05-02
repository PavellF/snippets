package exceptions;

@SuppressWarnings("serial")
public class IncorrectExpressionException  extends RuntimeException{

	private String message;
	private final static int code = 1;
	
	public IncorrectExpressionException(String message){
		this.message = message;
	}
	
	public IncorrectExpressionException(){}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static int getCode() {
		return code;
	}
	
}

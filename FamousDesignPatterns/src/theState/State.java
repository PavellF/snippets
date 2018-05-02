package theState;

public interface State {

	public void ordered(Object data);
	
	public void moneyRecieved(Object data);
	
	public void refused(Object data);
	
	public void sendedToCustomer(Object data);
	
	public void wait(Object data);
	
	public void recievedByCustomer(Object data);
	
	public void acceptedByCustomer(Object data);
	
}

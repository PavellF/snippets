package modelViewController;

public interface ControllerInterface {

	public void doRemoteRequest();
	
	public void place(Order order);
	
	public void deleteOrder(String name);
	
	public void updateAll();
	
}

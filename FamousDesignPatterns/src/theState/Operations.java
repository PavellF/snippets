package theState;

public interface Operations {

	public void order(Customer customer);
	
	public void pay(Customer customer);
	
	public void refuse(Customer customer);
	
	public void sendToCustomer(Customer customer);
	
	public void pending(Customer customer);
	
	public void recieveItem(Customer customer);
	
	public void acceptItem(Customer customer);
		
}

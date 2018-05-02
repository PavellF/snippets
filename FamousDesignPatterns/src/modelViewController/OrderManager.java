package modelViewController;

import java.util.List;

public interface OrderManager {

	public void placeOrder(Order order);
	
	public void deleteOrderByName(String name);
	
	public void registerObserver(OrderObserver oo);
	  
	public void removeObserver(OrderObserver oo);
	
	public List<Order> getAllOrders();

}

package modelViewController;

import java.util.ArrayList;
import java.util.List;

public class OrderManagerImpl implements OrderManager {

	private List<OrderObserver> observers;
	
	public OrderManagerImpl () {
		observers = new ArrayList<>();
	}
	
	@Override
	public void placeOrder(Order order) {
		OrderDB.persist(order);
		notifyObservers();
	}

	@Override
	public void deleteOrderByName(String name) {
		OrderDB.delete(name);
		notifyObservers();
	}

	@Override
	public void registerObserver(OrderObserver oo) {
		observers.add(oo);

	}

	@Override
	public void removeObserver(OrderObserver oo) {
		int i = observers.indexOf(oo);
		if (i >= 0) {
			observers.remove(i);
		}

	}
	
	private void notifyObservers() {
		
		observers.forEach((val) -> {
			val.orderUpdated();
		});
		
	}

	@Override
	public List<Order> getAllOrders() {
		return OrderDB.readAll();
	}

}

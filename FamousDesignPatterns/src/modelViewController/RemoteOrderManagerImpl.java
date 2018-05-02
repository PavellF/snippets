package modelViewController;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RemoteOrderManagerImpl implements RemoteOrderManager {

	private List<RemoteOrderObserver> observers;
	
	
	public RemoteOrderManagerImpl () {
		observers = new ArrayList<>();
		
	}
	
	//simulates network request
	private Order networkRequest() {
		return new Order(new Date(),"New TV set" ,"John Doe");
	}

	@Override
	public void registerObserver(RemoteOrderObserver ooo) {
		observers.add(ooo);

	}

	@Override
	public void removeObserver(RemoteOrderObserver ooo) {
		int i = observers.indexOf(ooo);
		if (i >= 0) {
			observers.remove(i);
		}
	}
	
	private void notifyObservers() {
		
		observers.forEach((val) -> {
			val.remoteOrderUpdated();
		});
		
	}

	@Override
	public List<Order> getRemotes() {
		
		Order first = networkRequest();
		Order second = networkRequest();
		
		List<Order> list = new ArrayList<>();
		list.add(first);
		list.add(second);
		notifyObservers();
		
		return list;
	}

}

package modelViewController;

import java.util.List;

public interface RemoteOrderManager {

	public List<Order> getRemotes();
	
	public void registerObserver(RemoteOrderObserver ooo);
	  
	public void removeObserver(RemoteOrderObserver ooo);
		
}

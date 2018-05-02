package modelViewController;

import java.util.ArrayList;
import java.util.List;

public class OrderDB {

	private static final List<Order> DB = new ArrayList<>();
	
	public static void persist(Order order) {
		DB.add(order);
	}
	
	public static void delete(final String name) {
		
		for(int i = 0; i < DB.size(); i++) {
			if (DB.get(i).getName().equals(name)) {
				DB.remove(i);
			}
		}
		
	}
	
	public static List<Order> readAll() {
		return DB;
	}
	
}

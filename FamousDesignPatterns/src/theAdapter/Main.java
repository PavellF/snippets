package theAdapter;

/**
 * 
 * The Adapter Pattern converts the interface of a class 
 * into another interface the clients expect. Adapter 
 * lets classes work together that couldn’t 
 * otherwise because of incompatible interfaces.
 *  
 **/

public class Main {

	public static void main(String[] args) {
		
		MessageServiceSecured adapter = 
				new Adapter(new MessageServiceImpl());
		
		NotificationManager nm = new NotificationManager(adapter);
		
		nm.sendToAll();
		nm.handle();
		//throws an error due underlying MessageService instead 
		//MessageServiceSecured that NotificationManager expected
		nm.sendToAllSecure();
		
	}

}

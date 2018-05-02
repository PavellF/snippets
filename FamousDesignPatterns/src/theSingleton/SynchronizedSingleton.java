package theSingleton;

public class SynchronizedSingleton {

	private static SynchronizedSingleton synchronizedSingleton;

	private SynchronizedSingleton() { }
	
	//synchronized - force every thread to wait its turn before 
	//it can enter the method. No two threads may enter the 
	//method at the same time.
	public static synchronized SynchronizedSingleton instantiate() {
		
		if (synchronizedSingleton == null) {
			synchronizedSingleton = new SynchronizedSingleton();
		}
		
		return synchronizedSingleton;
	}
	
	public void doAwesomeStuff() {
		System.out.println("*very complicated business logic. Now"
				+ " is synchronized.*");
	}
	
}

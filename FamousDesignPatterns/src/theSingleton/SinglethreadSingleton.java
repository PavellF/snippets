package theSingleton;

public class SinglethreadSingleton {

	private static SinglethreadSingleton singlethreadSingleton;
	
	private SinglethreadSingleton() { }
	
	public static SinglethreadSingleton instance() {
		
		if (singlethreadSingleton == null) {
			singlethreadSingleton = new SinglethreadSingleton();
		}
		
		return singlethreadSingleton;
	}

	public void doAwesomeStuff() {
		System.out.println("*very complicated business logic*");
	}

}

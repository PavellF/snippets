package theSingleton;

public class DoubleCheckedLockingSingleton {

	//volatile - ensures that multiple threads handle the 
	//dcls variable correctly when it is being initialized to the 
	//DoubleCheckedLockingSingleton instance. 
	//(always actual value for each thread)
	private volatile static DoubleCheckedLockingSingleton dcls;
	
	private DoubleCheckedLockingSingleton() { }
	
	public static DoubleCheckedLockingSingleton instantiate() {
		
		if (dcls == null) {
			
			synchronized (DoubleCheckedLockingSingleton.class) {
				if (dcls == null) {
					dcls = new DoubleCheckedLockingSingleton();
				}
			}
			
		}
		
		return dcls;
		
	}
	
	public void doAwesomeStuff() {
		System.out.println("*very complicated business logic. Now is "
				+ "instantiated with volatile keyword.*");
	}
	
}

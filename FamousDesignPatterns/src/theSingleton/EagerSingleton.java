package theSingleton;

//This code is considered as thread safe.
public class EagerSingleton {

	private static EagerSingleton eagerSingleton = 
			new EagerSingleton();
	
	private EagerSingleton() { }
	
	public static EagerSingleton get() {
		return eagerSingleton;
	}
	
	public void doAwesomeStuff() {
		System.out.println("*very complicated business logic. Now is "
				+ "instantiated at compile time.*");
	}
	
}

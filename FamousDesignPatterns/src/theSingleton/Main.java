package theSingleton;

/**
 *  The Singleton Pattern ensures a class has only one instance, 
 *  and provides a global point of access to it.
 **/

public class Main {

	public static void main(String[] args) {
		
		SinglethreadSingleton ss = SinglethreadSingleton.instance();
		SinglethreadSingleton ss1 = SinglethreadSingleton.instance();
		
		//do they point to the same object in the memory
		if (ss == ss1) {
			ss.doAwesomeStuff();
		}
		
		EagerSingleton.get().doAwesomeStuff();
		
		DoubleCheckedLockingSingleton.instantiate().doAwesomeStuff();
		
		SynchronizedSingleton.instantiate().doAwesomeStuff();
		
	}

}

package theProxy.protection;

/**
 * 
 * The Proxy Pattern provides a surrogate or placeholder 
 * for another object to control access to it.
 *
 **/

public class Main {

	public static void main(String[] args) throws IllegalAccessException {
		
		User me = new User("Ann Lee");
		UserRepository ur = new UserRepositoryImpl();
		UserRepository gurih = new GuestUserRepositoryInvocationHandler(ur);
		UserRepository rurih = new RegularUserRepositoryInvocationHandler(ur, me);
		UserRepository surih = new SuperUserRepositoryInvocationHandler(ur);
		
		//gurih.deleteUser(new User("Disliked")); //throws an exception!
		gurih.fetchById(44);
		
		rurih.update(me);
		//rurih.update(new User("It is not me")); //throws an exception!
		
		surih.deleteUser(new User("Unwanted"));
		surih.update(new User("Want to update"));

	}

}

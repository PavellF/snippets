package theFactory.abstractF;

/**
 *	OO Principle
 *
 *	Depend on abstractions. Do not depend on concrete classes.
 * 
 * 	The Abstract Factory Pattern provides an interface for 
 * 	creating families of related or dependent objects without 
 * 	specifying their concrete classes.
 * 
 **/

public class Main {

	public static void main(String[] args) {
		
		AccountBuilder ab = new BusinessAccountBuilder();
		
		Account a = ab.processAccount(AccountType.CHECKING);
		
		System.out.println(a.getAlias());

	}

}

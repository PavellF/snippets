package theStrategy;

/**
 * Strategy - defines a family of algorithms, encapsulates 
 * each one, and makes them interchangeable. Strategy lets the 
 * algorithm vary independently from clients that use it.
 * 
 * "OO Principles"
 * Encapsulate what varies.
 * Favor composition over inheritance.
 * Program to interfaces, not implementations.
 * */

public class Main {

	public static void main(String[] args) {
		
		final Human bowman = new Bowman(new Bow(), new Silent());
		bowman.looking();
		bowman.performAttack();
		System.out.println(bowman.saySomething());
		
		// change behavior at runtime:
		
		final Human crusader = new Crusader();
		System.out.println(crusader.saySomething());
		crusader.setConversationBehavior(new Rude());
		System.out.println(crusader.saySomething());
		
	}

}

package theDecorator;

import java.math.BigDecimal;

/**
 * OO principle
 * Classes should be open for extension but closed for modification.
 * 
 * The Decorator Pattern attaches additional responsibilities to an 
 * object dynamically. Decorators provide a flexible alternative to 
 * subclassing for extending functionality.
 * 
 **/

public class Main {

	public static void main(String[] args) {
		
		Transaction t = new Credit(new BigDecimal(9000));
		
		t = new BankServiceTax(t);
		t = new StateTax(t);
		
		System.out.println("Current balance "+t.performTransaction()
			+ "\n" + t.getDescription());
		
		
		
		Transaction t2 = new Debit(new BigDecimal(200));
		System.out.println("Current balance "+t2.performTransaction()
			+ "\n" + t2.getDescription());

	}

}

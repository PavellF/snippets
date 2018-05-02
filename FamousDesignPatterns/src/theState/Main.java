package theState;

import java.math.BigDecimal;

/**
 * 
 * The State Pattern allows an object to alter its 
 * behavior when its internal state changes. The 
 * object will appear to change its class.
 * 
 **/

public class Main {

	public static void main(String[] args) {
		
		Unit book = new Book(new BigDecimal(59.90), "Design Patterns");
		Customer c = new Customer(new BigDecimal(600));
		Operations bp = new BuyingProcessor(book, 10);
		
		System.out.println(bp.toString());
		bp.pay(c);
		System.out.println(bp.toString());
		System.out.println();
		
		bp.order(c);
		bp.pay(c);
		bp.refuse(c);
		System.out.println(c.toString());
		System.out.println(bp.toString());
		System.out.println();
		
		bp.order(c);
		bp.pay(c);
		bp.sendToCustomer(c);
		bp.pending(c);
		bp.recieveItem(c);
		bp.acceptItem(c);
		System.out.println(c.toString());
		System.out.println(bp.toString());
		System.out.println();

		bp.order(c);
		bp.pay(c);
		bp.sendToCustomer(c);
		bp.pending(c);
		bp.recieveItem(c);
		bp.acceptItem(c);
		bp.refuse(c);
		System.out.println(c.toString());
		System.out.println(bp.toString());
		System.out.println();
		
		bp.order(c);
		bp.pay(c);
		bp.sendToCustomer(c);
		bp.pending(c);
		System.out.println(c.toString());
		bp.recieveItem(c);
		bp.refuse(c);
		bp.acceptItem(c);
		System.out.println(c.toString());
		System.out.println(bp.toString());
		System.out.println();
		
		bp.recieveItem(c);
		bp.refuse(c);
		bp.acceptItem(c);
		bp.order(c);
		bp.pay(c);
		bp.sendToCustomer(c);
		bp.pending(c);
		System.out.println(c.toString());
		System.out.println(bp.toString());
		
		
		
		
	}

}

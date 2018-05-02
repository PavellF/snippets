package theMemento;

import java.math.BigDecimal;

public class Client {

	public static void main(String[] args) {
		
		TransactionOriginator to = new TransactionOriginator(
				new Long(1), "199384", "245337", new BigDecimal(1990.00));
		TransactionMemento tm = to.save();
		TransactionCaretaker tc = new TransactionCaretaker();
		tc.addMemento(tm);
		
		System.out.println("Original transaction "+to.toString());
		System.out.println("User goes back and change his account number..");
		
		to.setAccountNumberFrom("200384");
		
		System.out.println("After 1st change "+to.toString());
		System.out.println("User goes back and change destination account number..");
		
		tm = to.save();
		tc.addMemento(tm);
		to.setAccountNumberTo("454566");
		
		System.out.println("After 2st change "+to.toString());
		System.out.println("User undoes destination account number..");
		
		to.undo(tc.getMemento());
		
		System.out.println("After 1st undo "+to.toString());
		System.out.println("User undoes his account number..");
		
		to.undo(tc.getMemento());
		
		System.out.println("After 2st undo "+to.toString());
		

	}

}

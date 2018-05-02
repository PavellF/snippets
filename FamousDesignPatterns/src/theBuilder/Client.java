package theBuilder;

import java.math.BigDecimal;

public class Client {

	public static void main(String[] args) {
		
		BankAccount account = new BankAccount.Builder(1)
				.withOwner("Anna")
				.atBranch("Shanghai")
				.atRate(new BigDecimal(1.5))
				.withBalance(new BigDecimal(250))
				.build();
		
		Director d = new Director();
		BankAccount.Builder builder = new BankAccount.Builder(2);
		d.buildRegularAccount(builder, "John", "Lillehammer");
		 
		BankAccount anotherAccount = builder.build();
			
		System.out.println(account);
		System.out.println(anotherAccount);

	}

}

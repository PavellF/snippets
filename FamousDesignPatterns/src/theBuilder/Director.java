package theBuilder;

import java.math.BigDecimal;

public class Director {

	public void buildRegularAccount(BankAccount.Builder builder, String owner, String branch) {
		
		builder
		.atBranch(branch)
		.atRate(new BigDecimal(2.5))
		.withBalance(new BigDecimal(0.00))
		.withOwner(owner);
		
	}
	
	public void buildBusinessAccount(BankAccount.Builder builder, String owner, String branch) {
		
		builder
		.atBranch(branch)
		.atRate(new BigDecimal(1.33))
		.withBalance(new BigDecimal(0.00))
		.withOwner(owner);
		
	}
	
}

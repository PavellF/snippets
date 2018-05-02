package theDecorator;

import java.math.BigDecimal;

public class StateTax extends TaxDecorator {

	private Transaction transaction;
	private static final BigDecimal TAX_AMOUNT = new BigDecimal(100);
	
	public StateTax(Transaction transaction) {
		this.transaction = transaction;
	}
	
	@Override
	public String getDescription() {
		return transaction.getDescription() + 
				"Also a state tax was charged: " +
				TAX_AMOUNT + "\n";
	}

	@Override
	public BigDecimal performTransaction() {
		return transaction.performTransaction().subtract(TAX_AMOUNT);
	}


}

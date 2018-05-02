package theDecorator;

import java.math.BigDecimal;

public class Debit extends Transaction {

	private BigDecimal amount;
	
	public Debit(BigDecimal amount) {
		super.setDescription(
			"A debit transaction. Initial amount: " + amount + "\n");
		this.amount = amount;
	}
	
	@Override
	public BigDecimal performTransaction() {
		return amount;
	}

}

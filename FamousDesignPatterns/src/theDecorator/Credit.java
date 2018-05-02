package theDecorator;

import java.math.BigDecimal;

public class Credit extends Transaction {

	private BigDecimal amount;
	
	public Credit(BigDecimal amount) {
		super.setDescription(
			"A credit transaction. Initial amount: " + amount + "\n");
		this.amount = amount;
	}
	
	@Override
	public BigDecimal performTransaction() {
		return amount;
	}

}

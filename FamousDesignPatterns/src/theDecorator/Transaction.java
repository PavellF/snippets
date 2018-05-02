package theDecorator;

import java.math.BigDecimal;

public abstract class Transaction {

	private String description = "Generic transaction.";
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public abstract BigDecimal performTransaction();
	
}

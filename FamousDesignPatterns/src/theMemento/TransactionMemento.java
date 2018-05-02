package theMemento;

import java.math.BigDecimal;

public class TransactionMemento {

	private final String accountNumberTo;
	private final String accountNumberFrom;
	private final BigDecimal amount;
	
	public TransactionMemento(String accountNumberTo, String accountNumberFrom, BigDecimal amount) {
		this.accountNumberTo = accountNumberTo;
		this.accountNumberFrom = accountNumberFrom;
		this.amount = amount;
	}

	public String getAccountNumberTo() {
		return accountNumberTo;
	}

	public String getAccountNumberFrom() {
		return accountNumberFrom;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "TransactionMemento [accountNumberTo=" + accountNumberTo
				+ ", accountNumberFrom=" + accountNumberFrom + ", amount=" + amount + "]";
	}
	
}

package theMemento;

import java.math.BigDecimal;

public class TransactionOriginator {

	private Long transactionId;
	private String accountNumberTo;
	private String accountNumberFrom;
	private BigDecimal amount;
	
	public TransactionOriginator(Long transactionId, String accountNumberTo, String accountNumberFrom,
			BigDecimal amount) {
		this.transactionId = transactionId;
		this.accountNumberTo = accountNumberTo;
		this.accountNumberFrom = accountNumberFrom;
		this.amount = amount;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getAccountNumberTo() {
		return accountNumberTo;
	}

	public void setAccountNumberTo(String accountNumberTo) {
		this.accountNumberTo = accountNumberTo;
	}

	public String getAccountNumberFrom() {
		return accountNumberFrom;
	}

	public void setAccountNumberFrom(String accountNumberFrom) {
		this.accountNumberFrom = accountNumberFrom;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public TransactionMemento save() {
		return new TransactionMemento(
				accountNumberTo, 
				accountNumberFrom, 
				amount);
		
	}
	
	public void undo(TransactionMemento tm) {
		
		this.accountNumberTo = tm.getAccountNumberTo();
		this.accountNumberFrom = tm.getAccountNumberFrom();
		this.amount = tm.getAmount();
		
	}

	@Override
	public String toString() {
		return "TransactionOriginator [transactionId=" + transactionId + ", accountNumberTo=" + accountNumberTo
				+ ", accountNumberFrom=" + accountNumberFrom + ", amount=" + amount + "]";
	}
	
	
	
	
}

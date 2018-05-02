package theFactory.abstractF;

import java.math.BigDecimal;
import java.util.List;

public abstract class Account {

	private BigDecimal balance;
	
	private String alias;
	
	private Adress adress;
	
	private Name name;
	
	private List<Transaction> transactions;

	public abstract void showFullAccountInfo();
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	
	
}

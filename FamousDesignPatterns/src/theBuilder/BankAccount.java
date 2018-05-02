package theBuilder;

import java.math.BigDecimal;

public class BankAccount {

	public static class Builder {
		
		private long accountId; //This is important, so we'll pass it to the constructor.
		private String ownerName;
		private String branch;
		private BigDecimal balance;
		private BigDecimal rate;
		
		public Builder(long accountId) {
			this.accountId = accountId;
		}
		
		public Builder withOwner(String name) {
			this.ownerName = name;
			
			return this;
		}
		
		public Builder atBranch(String name) {
			this.branch = name;
			
			return this;
		}
		
		public Builder withBalance(BigDecimal val) {
			this.balance = val;
			
			return this;
		}
		
		public Builder atRate(BigDecimal val) {
			this.rate = val;
			
			return this;
		}
		
		public BankAccount build() {
			
			final BankAccount account = new BankAccount();
			account.accountId = this.accountId;
			account.ownerName = this.ownerName;
			account.branch = this.branch;
			account.balance = this.balance;
			account.rate = this.rate;
			
			return account;
			
		}
		
	}
	
	private long accountId;
	private String ownerName;
	private String branch;
	private BigDecimal balance;
	private BigDecimal rate;
	
	private BankAccount(long accountId, String ownerName, String branch, BigDecimal balance, BigDecimal rate) {
		super();
		this.accountId = accountId;
		this.ownerName = ownerName;
		this.branch = branch;
		this.balance = balance;
		this.rate = rate;
	}
	
	private BankAccount() {}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "BankAccount [accountId=" + accountId + ", ownerName=" + ownerName + ", branch=" + branch + ", balance="
				+ balance + ", rate=" + rate + "]";
	}
	
	
	
}

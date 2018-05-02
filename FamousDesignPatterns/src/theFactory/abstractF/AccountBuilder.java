package theFactory.abstractF;

public abstract class AccountBuilder {

	protected abstract Account registerNew(AccountType accountType);
	
	public final Account processAccount(AccountType accountType) {
		
		Account account = registerNew(accountType);
		
		account.showFullAccountInfo();
		
		return account;
		
	}
	
}

package theFactory.abstractF;

public class BusinessAccountBuilder extends AccountBuilder {

	@Override
	protected Account registerNew(AccountType accountType) {
		
		Account account = null;
		final AccountAbstractFactory aaf = 
				new BusinessAccountAbstractFactory();
		
		switch(accountType) {
		case CHECKING: {
			account = new Checking(aaf);
			account.setAlias("checking bus");
		}
			break;
		case DEPOSIT: {
			account = new Deposit(aaf);
			account.setAlias("deposit bus");
		}
			break;
		default: return null;
		
		}
		
		return account;
	}

}

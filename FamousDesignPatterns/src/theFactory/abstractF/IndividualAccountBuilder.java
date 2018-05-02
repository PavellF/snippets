package theFactory.abstractF;

public class IndividualAccountBuilder extends AccountBuilder {

	@Override
	protected Account registerNew(AccountType accountType) {

		Account account = null;
		final AccountAbstractFactory aaf = 
				new IndividualAccountAbstractFactory();
		
		switch(accountType) {
		case CHECKING: {
			account = new Checking(aaf);
			account.setAlias("checking ind");
		}
			break;
		case DEPOSIT: {
			account = new Deposit(aaf);
			account.setAlias("deposit ind");
		}
			break;
		default: return null;
		
		}
		
		return account;
	}

}

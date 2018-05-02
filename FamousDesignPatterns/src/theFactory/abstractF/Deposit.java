package theFactory.abstractF;

public class Deposit extends Account {

	private AccountAbstractFactory aaf;
	
	public Deposit(AccountAbstractFactory aaf) {
		super();
		this.aaf = aaf;
	}

	@Override
	public void showFullAccountInfo() {
		System.out.println("Full account info for individual.");
		super.setAdress(aaf.createAdress());
		super.setTransactions(aaf.createTransactions());
		super.setName(aaf.createName());
	}

}

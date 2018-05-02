package theFactory.abstractF;

public class Checking extends Account {

	private AccountAbstractFactory aaf;
	
	public Checking(AccountAbstractFactory aaf) {
		super();
		this.aaf = aaf;
	}

	@Override
	public void showFullAccountInfo() {
		System.out.println("Full account info for business.");
		super.setAdress(aaf.createAdress());
		super.setTransactions(aaf.createTransactions());
		super.setName(aaf.createName());
	}

}

package theFactory.abstractF;

import java.util.List;

public interface AccountAbstractFactory {

	public Adress createAdress();
	
	public Name createName();
	
	public List<Transaction> createTransactions();
	
}

package theFactory.abstractF;

import java.util.ArrayList;
import java.util.List;

public class BusinessAccountAbstractFactory implements AccountAbstractFactory {

	@Override
	public Adress createAdress() {
		return new BusinessAdress();
	}

	@Override
	public Name createName() {
		return new BusinessName();
	}

	@Override
	public List<Transaction> createTransactions() {

		ArrayList<Transaction> list = 
				new ArrayList<>();
		
		list.add(new BusinessTransaction());
		list.add(new BusinessTransaction());
		
		return list;
	}

}

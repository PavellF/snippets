package theFactory.abstractF;

import java.util.ArrayList;
import java.util.List;

public class IndividualAccountAbstractFactory implements AccountAbstractFactory {

	@Override
	public Adress createAdress() {
		return new IndividualAdress();
	}

	@Override
	public Name createName() {
		return new IndividualName();
	}

	@Override
	public List<Transaction> createTransactions() {
		
		ArrayList<Transaction> list = 
				new ArrayList<>();
		
		list.add(new IndividualTransaction());
		list.add(new IndividualTransaction());
		
		return list;
	}

}

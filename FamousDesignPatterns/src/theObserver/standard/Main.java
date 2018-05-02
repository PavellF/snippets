package theObserver.standard;

import java.math.BigDecimal;
import java.util.Date;

/*
Design Principle
Strive for loosely coupled designs between objects that interact.

The Observer Pattern defines a one-to-many dependency between objects 
so that when one object changes state, all of its dependents are
notified and updated automatically.

*/

public class Main {

	public static void main(String[] args) {

		final StockExchange stockExchange = new StockExchange();

		final BrokerClient brokerClient = new BrokerClient(stockExchange);
		final BankClient bankClient = new BankClient(stockExchange);

		stockExchange.setRates(new DataNode()
				.setChangeRate(new BigDecimal(100))
				.setOperationDate(new Date()));

		stockExchange.setRates(new DataNode()
				.setChangeRate(new BigDecimal(99))
				.setOperationDate(new Date()));

		stockExchange.setRates(new DataNode()
				.setChangeRate(new BigDecimal(99))
				.setOperationDate(new Date()));

	}

}

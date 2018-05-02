package theObserver.javaPreDefined;

import java.math.BigDecimal;
import java.util.Date;

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

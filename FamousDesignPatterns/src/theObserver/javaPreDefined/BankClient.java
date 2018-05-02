package theObserver.javaPreDefined;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

public class BankClient implements Exchangeable, Observer {

	private Observable stockExchange;
	private DataNode pervious;
	
	public BankClient(Observable stockExchange) {
		this.stockExchange = stockExchange;
		stockExchange.addObserver(this);
		
		pervious = new DataNode();
		pervious.setChangeRate(new BigDecimal(0.00))
		.setOperationDate(new Date());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (o instanceof StockExchange) {
			
			/*
			Fetch only needed properties:
			
			StockExchange se = (StockExchange) o;
			final DataNode dataSet = se.getDataNode();
			*/
			
			final DataNode dataSet = (DataNode) arg;
			final BigDecimal newValue = dataSet.getChangeRate();
			final BigDecimal oldValue = pervious.getChangeRate();
			
			if(newValue.compareTo(oldValue) == 1) {
				positive();
			} else if(newValue.compareTo(oldValue) == -1) {
				negative();
			} else {
				neutral();
			}

			pervious = dataSet;
		}
		
	}

	@Override
	public void positive() {
		System.out.println("Stock went up...");

	}

	@Override
	public void negative() {
		System.out.println("Stock went down...");

	}

	@Override
	public void neutral() {
		System.out.println("Nothing changed.");

	}

}

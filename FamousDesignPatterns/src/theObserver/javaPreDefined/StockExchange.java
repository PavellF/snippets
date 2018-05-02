package theObserver.javaPreDefined;

import java.util.Observable;

public class StockExchange extends Observable {

	private DataNode data;
	
	public StockExchange() { }
	
	public void rateChanged() {
		setChanged();
		
		//notifyObservers();
		
		notifyObservers(data);
	}
	
	// simulate 3rd party API
	public void setRates(DataNode data) {
		
		this.data = data;
		rateChanged();
		
	}
	
	public DataNode getDataNode() {
		return data;
	}

}

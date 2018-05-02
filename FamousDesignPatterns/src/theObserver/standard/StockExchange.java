package theObserver.standard;

import java.util.ArrayList;
import java.util.List;

public class StockExchange implements Subject {

	private final List<Observer> observers;
	private DataNode data;
	
	public StockExchange() {
		this.observers = new ArrayList<>();;
	}
	
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
		
	}

	@Override
	public void removeObserver(Observer o) {
		final int i = observers.indexOf(o);
		
		if(i >= 0) {
			observers.remove(i);
		}
		
	}

	@Override
	public void notifyObserver() {
		
		observers.forEach((val) -> {
			
			final Observer o = (Observer) val;
			o.update(data);
			
		});
		
	}
	
	public void rateChanged() {
		notifyObserver();
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

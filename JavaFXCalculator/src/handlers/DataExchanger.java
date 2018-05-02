package handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataExchanger <T> {

	private Map<String,T> data;
	
	public DataExchanger(){
		data = new HashMap<String,T>();
	}
	
	public void addValue(String key, T value){
		data.put(key, value);
	}
	
	public T getValue(String name){
		return data.get(name);
	}
	
	public Set<String> showValues(){return data.keySet();}
}

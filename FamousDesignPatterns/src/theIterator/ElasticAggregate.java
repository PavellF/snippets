package theIterator;

import java.util.ArrayList;
import java.util.List;

public class ElasticAggregate <T> implements Aggregate<T>, Iterable<T> {

	private final List<T> list = new ArrayList<T>();
	
	@Override
	public T get(int pos) {
		return list.get(pos);
	}

	@Override
	public void push(T e) {
		list.add(e);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public Iterator<T> createIterator() {
		return new ElasticAggregateIterator<T>(list);
	}
}

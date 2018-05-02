package theIterator;

import java.util.List;

public class ElasticAggregateIterator<T> implements Iterator<T> {

	private final java.util.Iterator<T> listIterator;
	
	public ElasticAggregateIterator(List<T> list) {
		listIterator = list.iterator();
	}
	
	@Override
	public boolean hasNext() {
		return listIterator.hasNext();
	}

	@Override
	public T remove() {
		listIterator.remove();
		return null;
	}

	@Override
	public T next() {
		return listIterator.next();
	}

}

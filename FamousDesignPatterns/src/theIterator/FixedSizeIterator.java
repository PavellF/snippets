package theIterator;

public class FixedSizeIterator<T> implements Iterator<T> {

	private T[] items; 
	
	private int position = 0;
	
	public FixedSizeIterator(T[] items) {
		this.items = items;
	}
	
	@Override
	public boolean hasNext() {
		
		if (position >= items.length || items[position] == null) {
			return false;
		} else {
			return true;
		}
		
	}

	@Override
	public T remove() {
		
		if (position <= 0) {
			throw new IllegalStateException("At least one next() should be done.");
		}
		
		if (items[position-1] != null) {
			
			for (int i = position-1; i < (items.length-1); i++) {
				items[i] = items[i+1];
			}
			
			final T returned = items[items.length-1];
			
			items[items.length-1] = null;
			
			return returned;
		}
		
		return null;
	}

	@Override
	public T next() {
		final T item = items[position];
		position = position + 1;
		return item;
	}

}

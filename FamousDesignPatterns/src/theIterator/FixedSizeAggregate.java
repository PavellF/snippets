package theIterator;

@SuppressWarnings("unchecked")
public class FixedSizeAggregate<T> implements Aggregate<T>, Iterable<T> {

	final int maxItems;
	
	private Object[] items;
	
	private int numberOfItems = 0;
	
	public FixedSizeAggregate(int maxItems) {
		this.maxItems = maxItems;
		this.items = new Object[maxItems];
	}
	
	public FixedSizeAggregate () {
		this.maxItems = 10;
		this.items = new Object[maxItems];
	}
	
	
	@Override
	public T get(int pos) {
		
		if (pos >= 0 && pos < numberOfItems) {
			
			return (T) items[pos];
			
		} else {
			throw new IllegalStateException("Index must be greater than zero and less than"
					+ " number of items in array.");
		}
		
	}

	@Override
	public void push(T e) {
		
		if (numberOfItems >= maxItems) {
			throw new IllegalStateException("Array is already full.");
		} else {
			items[numberOfItems] = e;
			numberOfItems = numberOfItems + 1;
		}
		
	}

	@Override
	public int size() {
		return items.length;
	}

	@Override
	public Iterator<T> createIterator() {
		return new FixedSizeIterator<T>((T[]) items);
	}

}

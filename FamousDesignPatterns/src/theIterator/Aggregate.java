package theIterator;

public interface Aggregate <T> {
	
	public T get(int pos);
	
	public void push(T e);
	
	public int size();

}

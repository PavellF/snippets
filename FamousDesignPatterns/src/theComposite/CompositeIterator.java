package theComposite;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class CompositeIterator implements Iterator<EntityComponent> {

	private final Deque<Iterator<EntityComponent>> stack;
	
	public CompositeIterator(Iterator<EntityComponent> i) {
		stack = new ArrayDeque<>();
		stack.push(i);
	}
	
	@Override
	public boolean hasNext() {
		
		if (stack.isEmpty()) {
			return false;
		} else {
			
			Iterator<EntityComponent> i = stack.peek();
			
			if (!i.hasNext()) {
				stack.pop();
				return hasNext();
			} else {
				return true;
			}
			
		}
		
	}

	@Override
	public EntityComponent next() {
		
		if (hasNext()) {
			
			Iterator<EntityComponent> i = stack.peek();
			EntityComponent ec = i.next();
			stack.push(ec.createIterator());
			return ec;
			
		} 
		
		return null;
		
	}

}

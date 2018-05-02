package theComposite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityAggregate extends EntityComponent {

	private Iterator<EntityComponent> iterator = null;
	private final List<EntityComponent> list = new ArrayList<>();
	private String description;
	
	public EntityAggregate(String description) {
		this.description = description;
	}
	
	public EntityComponent getChild(int pos) {
		return list.get(pos);
	}
	
	public void push(EntityComponent ec) {
		list.add(ec);
	}
	
	public int size() {
		return list.size();
	}
	
	public void remove(EntityComponent ec) {
		list.remove(ec);
	}
	
	public String getDescription() {
		return description;
	}
	
	public Iterator<EntityComponent> createIterator() {
		
		if (iterator == null) {
			iterator = new CompositeIterator(list.iterator());
		} 
		
		return iterator;
		
	}
	
	public void print() {
		
		System.out.println(toString());
		/*
		final Iterator<EntityComponent> iterator = list.iterator();
		
		while (iterator.hasNext()) {
			EntityComponent menuComponent = iterator.next();
			menuComponent.print();
		}
		*/
	}

	@Override
	public String toString() {
		return "EntityAggregate [description=" + description + "]";
	}
	
	
}

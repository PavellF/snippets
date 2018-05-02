package theComposite;

import java.util.Iterator;

public class EntityManager {

	private final EntityComponent components;
	
	public EntityManager(EntityComponent components) {
		this.components = components;
	}
	
	public void print() {
		
		Iterator<EntityComponent> iterator = components.createIterator();
		
		while (iterator.hasNext()) {
			
			try {
				iterator.next().print();
			} catch (UnsupportedOperationException e) { }
			
		}
		
	}
	
	public void printOnlyLarge() {
		
		Iterator<EntityComponent> iterator = components.createIterator();
		
		while (iterator.hasNext()) {
			EntityComponent ec = iterator.next();
			try {
				
				if (ec.isLarge()) {
					ec.print();
				}
				
			} catch (UnsupportedOperationException e) { }
			
		}
		
	}
	
}

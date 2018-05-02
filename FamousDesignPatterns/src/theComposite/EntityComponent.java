package theComposite;

import java.util.Iterator;

public abstract class EntityComponent {
	
	public EntityComponent getChild(int pos) {
		throw new UnsupportedOperationException();
	}
	
	public void push(EntityComponent ec) {
		throw new UnsupportedOperationException();
	}
	
	public int size() {
		throw new UnsupportedOperationException();
	}
	
	public void remove(EntityComponent ec) {
		throw new UnsupportedOperationException();
	}
	
	
	public String getDescription() {
		throw new UnsupportedOperationException();
	}

	public int getWeight() {
		throw new UnsupportedOperationException();
	}

	public boolean isLarge() {
		throw new UnsupportedOperationException();
	}
	
	
	public abstract Iterator<EntityComponent> createIterator();
	
	public void print() {
		throw new UnsupportedOperationException();
	}
	
}

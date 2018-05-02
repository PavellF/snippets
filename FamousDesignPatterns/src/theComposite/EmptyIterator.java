package theComposite;

import java.util.Iterator;

public class EmptyIterator implements Iterator<EntityComponent> {

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public EntityComponent next() {
		return null;
	}

}

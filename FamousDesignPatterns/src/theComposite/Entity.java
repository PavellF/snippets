package theComposite;

import java.util.Iterator;

public class Entity extends EntityComponent {

	private String description;
	private int weight;
	private boolean isLarge;
	
	public Entity(String description, int weight) {
		this.description = description;
		this.weight = weight;
		this.isLarge = weight > 110;
	}

	@Override
	public String toString() {
		return "Entity [title=" + description + ", weight=" + weight + ", isLarge=" + isLarge + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isLarge ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + weight;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (isLarge != other.isLarge)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	@Override
	public Iterator<EntityComponent> createIterator() {
		return new EmptyIterator();
	}
	
	public String getDescription() {
		return description;
	}

	public int getWeight() {
		return weight;
	}

	public boolean isLarge() {
		return isLarge;
	}
	
	public void print() {
		
		System.out.println(toString());
		
	}
	
}

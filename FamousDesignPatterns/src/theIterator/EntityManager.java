package theIterator;

public class EntityManager {

	private Iterable<Entity>[] aggregates;
	
	@SafeVarargs
	public EntityManager(Iterable<Entity>... aggregates) {
		this.aggregates = aggregates;
	}
	
	public void print() {
		
		for (int i = 0; i < aggregates.length; i++) {
			System.out.println("\n---------------\n");
			print(aggregates[i].createIterator());
		}
		
	}
	
	private void print(Iterator<Entity> i) {
		
		while(i.hasNext()) {
			Entity e = (Entity) i.next();
			System.out.println(e.toString());
		}
		
	}
	
}

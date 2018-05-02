package theIterator;

/**
 * 
 * The Iterator Pattern provides a way to access the elements 
 * of an aggregate object sequentially without exposing 
 * its underlying representation.
 * 
 **/

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		Aggregate<Entity> fsa = new FixedSizeAggregate<>(10);
		fsa.push(new Entity("Some Entity 1", 100, 34, 35, 76));
		fsa.push(new Entity("Some Entity 2", 130, 374, 635, 176));
		fsa.push(new Entity("Some Entity 3", 8, 10, 6, 6));
		fsa.push(new Entity("Some Entity 4", 1008, 434, 535, 976));
		
		Aggregate<Entity> ea = new ElasticAggregate<>();
		ea.push(new Entity("Brand new Entity 1", 98, 324, 8, 763));
		ea.push(new Entity("Brand new Entity 2", 1, 434, 6, 743));
		ea.push(new Entity("Brand new Entity 3", 45, 6, 347, 7634));
		ea.push(new Entity("Brand new Entity 4", 19, 4, 54, 9));
		ea.push(new Entity("Brand new Entity 5", 36, 1, 33, 6));
		
		
		EntityManager em = new EntityManager((Iterable<Entity>) fsa, (Iterable<Entity>) ea);
		em.print();
		
	}

}

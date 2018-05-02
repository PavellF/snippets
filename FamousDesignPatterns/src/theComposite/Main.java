package theComposite;

/**
 * 
 * The Composite Pattern allows you to compose objects into tree structures 
 * to represent part-whole hierarchies. Composite lets clients treat 
 * individual objects and compositions of objects uniformly.
 *
 **/

public class Main {

	
	public static void main(String[] args) {
		
		EntityComponent levelTwo = new EntityAggregate("Level 2 three.");
		levelTwo.push(new Entity("Some level 3 Entity 1", 900));
		levelTwo.push(new Entity("Some level 3 Entity 2", 777));
		levelTwo.push(new Entity("Some level 3 Entity 3", 2));
		levelTwo.push(new Entity("Some level 3 Entity 4", 7));
		
		EntityComponent levelOne = new EntityAggregate("Level 1 three.");
		levelOne.push(new Entity("Some level 2 Entity 1", 232));
		levelOne.push(new Entity("Some level 2 Entity 2", 22));
		levelOne.push(levelTwo);
		
		EntityComponent root = new EntityAggregate("Three root.");
		root.push(new Entity("Some Entity 1", 100));
		root.push(levelOne);
		root.push(new Entity("Some Entity 2", 1300));
		root.push(new Entity("Some Entity 3", 8));
		
		
		EntityManager em = new EntityManager(root);
		//em.print();
		em.printOnlyLarge();
	}

}

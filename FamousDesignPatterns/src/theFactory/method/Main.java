package theFactory.method;

/**
 * OO guidlines:
 * 
 * No variable should hold a reference to a concrete class.
 * 
 * No class should derive from a concrete class.
 * 
 * No method should override an implemented method of any of 
 * its base classes.
 * 
 * The Factory Method Pattern defines an interface for creating 
 * an object, but lets subclasses decide which class to 
 * instantiate. Factory Method lets a class defer instantiation 
 * to subclasses.
 * 
 **/

public class Main {

	public static void main(String[] args) {
		
		Warehouse warehouse = new ChemicalWarehouse();
		Warehouse foodWarehouse = new FoodWarehouse();
		
		Entity bag = foodWarehouse.processEntity(EntityType.SMALL);
		Entity tank = warehouse.processEntity(EntityType.LARGE);
		
		
	}

}

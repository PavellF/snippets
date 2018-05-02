package theFactory.method;

public class FoodWarehouse extends Warehouse {

	@Override
	public Entity createEntity(EntityType entityType) {
		
		switch (entityType) {
			//some DB logic is possible
			case LARGE: return new Box();
			case MASSIVE: return new FridgeBox();
			case SMALL: return new FoodBag();
			default: return null;
		
		}
		
	}

}

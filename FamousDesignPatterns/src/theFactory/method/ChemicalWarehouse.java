package theFactory.method;

public class ChemicalWarehouse extends Warehouse {

	@Override
	public Entity createEntity(EntityType entityType) {
		
		switch(entityType) {
			//some DB logic is possible
			case LARGE: return new Tank();
			case MASSIVE: return new LargeTank();
			case SMALL: return new SteelBox();
			default: return null;
			
		}
	}

}

package theFactory.method;

public abstract class Warehouse {

	public final Entity processEntity(EntityType entityType) {
		
		Entity entity;
		
		entity = createEntity(entityType);
		
		entity.receive();
		entity.register();
		entity.send();
		
		return entity;
		
	}
	
	public abstract Entity createEntity(EntityType entityType);
	
}

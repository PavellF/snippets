package theFactory.method;

public class Tank extends Entity {

	public void receive() {
		System.out.println(
				super.getLabel() + "has recieved carefully.");
	}
	
}

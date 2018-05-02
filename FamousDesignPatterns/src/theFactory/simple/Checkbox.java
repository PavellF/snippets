package theFactory.simple;

public class Checkbox extends Button {

	@Override
	protected void onClick() {
		
		System.out.println("Checked");

	}

	@Override
	public void getReadyForUse() {
		
		System.out.println(this + " is ready for use!");

	}

}

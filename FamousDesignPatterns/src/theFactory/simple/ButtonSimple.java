package theFactory.simple;

public class ButtonSimple extends Button {

	@Override
	protected void onClick() {
		System.out.println("Clicked");

	}

	@Override
	public void getReadyForUse() {
		System.out.println(this + " is ready for use!");

	}

}

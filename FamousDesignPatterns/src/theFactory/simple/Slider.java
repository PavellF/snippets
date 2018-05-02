package theFactory.simple;

public class Slider extends Button {

	@Override
	protected void onClick() {
		
		System.out.println("Slides");

	}

	@Override
	public void getReadyForUse() {
		
		System.out.println(this + " is ready for use!");

	}

}

package theFactory.simple;

//client of the factory
public class DisplayElementProducer {

	private SimpleButtonFactory simpleButtonFactory;

	public DisplayElementProducer(SimpleButtonFactory simpleButtonFactory) {
		super();
		this.simpleButtonFactory = simpleButtonFactory;
	}
	
	public Button produceButton(ButtonType buttonType, String label) {
		
		Button button = simpleButtonFactory.
				createButton(buttonType);
		
		button.setLabel(label);
		button.getReadyForUse();
		
		return button;
	}
}

package theMediator;

public class TextBox implements AbstractComponent {

	private Mediator mediator;

	public void changed() {
		mediator.textBoxOnChange();
	}

	public Mediator getMediator() {
		return mediator;
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}
	
}

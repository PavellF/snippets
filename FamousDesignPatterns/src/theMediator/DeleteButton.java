package theMediator;

public class DeleteButton implements AbstractComponent {

	private String title;
	private Mediator mediator;
	
	public DeleteButton(String title) {
		this.title = title;
	}

	public void clicked() {
		mediator.deleteOnclick();
	}
	
	public void movedOn() {
		mediator.deleteOnMove();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Mediator getMediator() {
		return mediator;
	}

	public void setMediator(Mediator mediator) {
		this.mediator = mediator;
	}

	@Override
	public String toString() {
		return "Button [title=" + title + ", mediator=" + mediator + "]";
	}
	
}

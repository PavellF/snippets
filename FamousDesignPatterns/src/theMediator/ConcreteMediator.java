package theMediator;

public class ConcreteMediator extends Mediator {

	@Override
	public void deleteOnclick() {
		System.out.println("Button is clicked. "+super.getDeleteButton());

	}

	@Override
	public void deleteOnMove() {
		System.out.println("Mouse is moved on button. "+super.getDeleteButton());

	}

	@Override
	public void textBoxOnChange() {
		System.out.println("Text in text box had changed. "+super.getTextBox());

	}

}

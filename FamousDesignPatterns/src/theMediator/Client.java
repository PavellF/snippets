package theMediator;

public class Client {

	public static void main(String[] args) {
		
		Mediator mediator = new ConcreteMediator();
		mediator.setDeleteButton(new DeleteButton("Delete"));
		mediator.setTextBox(new TextBox());

		mediator.deleteOnclick();
		mediator.deleteOnMove();
		mediator.textBoxOnChange();
	}

}

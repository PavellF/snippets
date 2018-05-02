package theMediator;

public abstract class Mediator {

	private TextBox textBox;
	private DeleteButton deleteButton;
	
	public abstract void deleteOnclick();
	
	public abstract void deleteOnMove();
	
	public abstract void textBoxOnChange();

	public TextBox getTextBox() {
		return textBox;
	}

	public DeleteButton getDeleteButton() {
		return deleteButton;
	}

	public void setTextBox(AbstractComponent textBox) {
		
		if (!(textBox instanceof TextBox)) {
			return;
		}
		
		textBox.setMediator(this);
		this.textBox = (TextBox) textBox;
	}

	public void setDeleteButton(AbstractComponent deleteButton) {
		
		if (!(deleteButton instanceof DeleteButton)) {
			return;
		}
		
		deleteButton.setMediator(this);
		this.deleteButton = (DeleteButton) deleteButton;
	}
	
}

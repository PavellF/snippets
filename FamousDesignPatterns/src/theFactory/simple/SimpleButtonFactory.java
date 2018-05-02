package theFactory.simple;



public class SimpleButtonFactory {
	
	//may be static
	public Button createButton(ButtonType buttonType) {
		
		Button button;
		
		switch(buttonType) {
		case BUTTON_CHECKBOX: {
			button = new Checkbox()
					.setColor("RED")
					.setHeight(100)
					.setWidth(200);
		}
			break;
		case BUTTON_NEXT: {
			button = new ButtonSimple()
					.setColor("GREEN")
					.setHeight(50)
					.setWidth(200);
		}
			break;
		case BUTTON_SLIDER: {
			button = new Slider()
					.setColor("CRIMSON")
					.setHeight(77)
					.setWidth(400);
		}
			break;
		case BUTTON_UNDO: {
			button = new ButtonSimple()
					.setColor("RED")
					.setHeight(55)
					.setWidth(98);
		}
			break;
		default: return null;
			
		}
		
		return button;
		
	}
	
}

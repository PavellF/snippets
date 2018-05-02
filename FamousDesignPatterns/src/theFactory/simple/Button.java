package theFactory.simple;


public abstract class Button {

	private int width;
	
	private int height;
	
	private String label;
	
	private String color;
	
	public Button setLabel(String label) {
		this.label = label;
		return this;
	}
	
	public int getWidth() {
		return width;
	}

	public Button setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public Button setHeight(int height) {
		this.height = height;
		return this;
	}

	public String getLabel() {
		return label;
	}

	public String getColor() {
		return color;
	}

	public Button setColor(String color) {
		this.color = color;
		return this;
	}

	protected abstract void onClick();
	
	public abstract void getReadyForUse();
	
}

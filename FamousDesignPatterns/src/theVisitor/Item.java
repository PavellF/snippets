package theVisitor;

public abstract class Item {

	private String title;
	protected double price;
	
	public Item(String title) {
		super();
		this.title = title;
	}

	public abstract String accept(ItemVisitor visitor);

	//price * tax
	public abstract void setPrice(double price);
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}
	
	public void add(Item item) {
		throw new UnsupportedOperationException();
	}

}

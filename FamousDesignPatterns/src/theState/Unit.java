package theState;

import java.math.BigDecimal;

public abstract class Unit {

	private BigDecimal price;
	private String title;
	
	public Unit(BigDecimal price, String title) {
		this.price = price;
		this.title = title;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrioce(BigDecimal weight) {
		this.price = weight;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Unit [price=" + price + ", title=" + title + "]";
	}
	
}

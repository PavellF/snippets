package theState;

import java.math.BigDecimal;

public class Book extends Unit {

	public Book(BigDecimal price, String title) {
		super(price, title);
	}

	@Override
	public String toString() {
		return super.toString() + " Book []";
	}

}

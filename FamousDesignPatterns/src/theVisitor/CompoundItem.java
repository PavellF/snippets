package theVisitor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CompoundItem extends Item {

	private List<Item> list;
	
	public CompoundItem(String title) {
		super(title);
		list = new ArrayList<>();
		
	}
	
	@Override
	public String accept(ItemVisitor visitor) {
		return visitor.visitCompoundItem(this);
	}

	@Override
	public void setPrice(double price) { }
	
	@Override
	public void add(Item item) {
		super.price += item.price;
		list.add(item);
	}

	public List<Item> getList() {
		return list;
	}

}

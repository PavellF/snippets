package theVisitor;

import java.util.Date;

public class GameItem extends Item {

	private String sizeOfGb;
	private String developerName;
	private Date released;
	
	public GameItem(String title, String sizeOfGb, String developerName, Date released) {
		super(title);
		this.sizeOfGb = sizeOfGb;
		this.developerName = developerName;
		this.released = released;
	}

	@Override
	public String accept(ItemVisitor visitor) {
		return visitor.visitGameOnline(this);
	}

	@Override
	public void setPrice(double price) {
		super.price = price * 0.95;

	}

	public String getSizeOfGb() {
		return sizeOfGb;
	}

	public void setSizeOfGb(String sizeOfGb) {
		this.sizeOfGb = sizeOfGb;
	}

	public String getDeveloperName() {
		return developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	public Date getReleased() {
		return released;
	}

	public void setReleased(Date released) {
		this.released = released;
	}

}

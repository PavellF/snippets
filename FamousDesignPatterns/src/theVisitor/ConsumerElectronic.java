package theVisitor;

public class ConsumerElectronic extends Item {

	private String powerConsuming;
	private int weight;
	
	public ConsumerElectronic(String title, String powerConsuming, int weight) {
		super(title);
		this.powerConsuming = powerConsuming;
		this.weight = weight;
	}

	@Override
	public String accept(ItemVisitor visitor) {
		return visitor.visitConsumerElectronic(this);
	}

	@Override
	public void setPrice(double price) {
		super.price = price * 1.1;

	}

	public String getPowerConsuming() {
		return powerConsuming;
	}

	public void setPowerConsuming(String powerConsuming) {
		this.powerConsuming = powerConsuming;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}

package theVisitor;

public interface ItemVisitor {

	public String visitGameOnline(GameItem gameItem);
	
	public String visitConsumerElectronic(ConsumerElectronic consumerElectronic);
	
	public String visitCompoundItem(CompoundItem compoundItem);
	
}

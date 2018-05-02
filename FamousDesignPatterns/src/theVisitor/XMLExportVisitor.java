package theVisitor;

import java.util.List;

public class XMLExportVisitor implements ItemVisitor {

	public String export(Item... args) {
		
        StringBuilder sb = new StringBuilder();
        
        for (Item item : args) {
            sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
            sb.append(item.accept(this) + "\n");
            System.out.println(sb.toString());
            sb.setLength(0);
        }
        
        return sb.toString();
    }
	
	private String visitCompound(CompoundItem ci) {
		
        final StringBuilder sb = new StringBuilder();
        final List<Item> list = ci.getList();
        
        for (Item item : list) {
            String obj = item.accept(this);
            obj = "    " + obj.replace("\n", "\n    ") + "\n";
            sb.append(obj);
        }
        
        return sb.toString();
    }
	
	@Override
	public String visitGameOnline(GameItem gameItem) {
		return "<GameOnline>" + "\n" +
                "    <title>" + gameItem.getTitle() + "</title>" + "\n" +
                "    <releaseDate>" + gameItem.getReleased() + "</releaseDate>" + "\n" +
                "    <sizeGB>" + gameItem.getSizeOfGb() + "</sizeGB>" + "\n" +
                "    <price>" + gameItem.getPrice() + "</price>" + "\n" +
                "    <developer>" + gameItem.getDeveloperName() + "</developer>" + "\n" +
                "</GameOnline>";
	}

	@Override
	public String visitConsumerElectronic(ConsumerElectronic ce) {
		return "<ConsumerElectronic>" + "\n" +
                "    <title>" + ce.getTitle() + "</title>" + "\n" +
                "    <powerConsuming>" + ce.getPowerConsuming() + "</powerConsuming>" + "\n" +
                "    <price>" + ce.getPrice() + "</price>" + "\n" +
                "    <weight>" + ce.getWeight() + "</weight>" + "\n" +
                "</ConsumerElectronic>";
	}

	@Override
	public String visitCompoundItem(CompoundItem compoundItem) {
		return "<CompoundItem>" + "\n" +
                "   <title>" + compoundItem.getTitle() + "</title>" + "\n" +
                "   <price>" + compoundItem.getPrice() + "</price>" + "\n" +
                visitCompound(compoundItem) +
                "</CompoundItem>";
	}
	

}

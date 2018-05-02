package theVisitor;

import java.util.Date;

public class Client {

	public static void main(String[] args) {
		
		Item fridge = new ConsumerElectronic("BellFridge", "240WT", 100);
		fridge.setPrice(456);
		Item tv = new ConsumerElectronic("genericTV", "220WT", 17);
		tv.setPrice(900);
		Item gameDigitalCopy = new GameItem("Quake", "0.3GB", "id software", new Date());
		gameDigitalCopy.setPrice(19.00);
		
		Item compound = new CompoundItem("Buy fridge, tv and video game with for 95%!");
		compound.add(fridge);
		compound.add(tv);
		compound.add(gameDigitalCopy);
		
		Item anotherCompound = new CompoundItem("Another game");
		anotherCompound.add(gameDigitalCopy);
		compound.add(anotherCompound);
		
		XMLExportVisitor exportVisitor = new XMLExportVisitor();
        System.out.println(exportVisitor.export(fridge, compound));
	}

}

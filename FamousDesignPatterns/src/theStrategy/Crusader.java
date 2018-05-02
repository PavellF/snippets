package theStrategy;

public class Crusader extends Human {

	public Crusader() {
		super.setBattleStyle(new Longswords());
		super.setConversationBehavior(new Silent());
	}
	
	public Crusader(BattleStyle battleStyle, ConversationBehavior conversationBehavior) {
		super.setBattleStyle(battleStyle);
		super.setConversationBehavior(conversationBehavior);
	}
	
	@Override
	public void looking() {
		System.out.println("Iron helmet, heavy armor.");

	}

}

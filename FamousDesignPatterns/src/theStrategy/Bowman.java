package theStrategy;

public class Bowman extends Human {

	public Bowman(BattleStyle battleStyle, ConversationBehavior conversationBehavior) {
		super.setBattleStyle(battleStyle);
		super.setConversationBehavior(conversationBehavior);
	}
	
	@Override
	public void looking() {
		System.out.println("No helmet, light armor.");
		
	}

}

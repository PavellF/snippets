package theStrategy;

public abstract class Human {

	// HAS-A - composition
	private BattleStyle battleStyle;
	private ConversationBehavior conversationBehavior;
	
	public Human() { }
	
	public abstract void looking();
	
	public void performAttack() {
		battleStyle.attack();
	}
	
	public String saySomething() {
		return conversationBehavior.sayCatchphraseOfTheDay();
	}
	
	public void walk() {
		System.out.print("All humans can walk.");
	}

	public BattleStyle getBattleStyle() {
		return battleStyle;
	}

	public void setBattleStyle(BattleStyle battleStyle) {
		this.battleStyle = battleStyle;
	}

	public ConversationBehavior getConversationBehavior() {
		return conversationBehavior;
	}

	public void setConversationBehavior(ConversationBehavior conversationBehavior) {
		this.conversationBehavior = conversationBehavior;
	}
	
}

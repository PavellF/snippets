package theStrategy;

public class Rude implements ConversationBehavior {

	@Override
	public String sayCatchphraseOfTheDay() {
		
		return "*uncensored word*";
	}

}

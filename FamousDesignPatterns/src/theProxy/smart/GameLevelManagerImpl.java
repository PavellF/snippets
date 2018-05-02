package theProxy.smart;

public class GameLevelManagerImpl implements GameLevelManager {

	@Override
	public void increaseModifier() {
		System.out.println("Modifier is increased.");

	}

	@Override
	public void skipLevel() {
		System.out.println("Player does not like this level...");

	}

	@Override
	public void takeHelp() {
		System.out.println("Too hard, taking help...");

	}

}

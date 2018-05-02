package theProxy.smart;

public class GameAccessControl implements GameLevelManager {

	private GameLevelManager gameAccessControl;
	private int increaseModifierCounter = 0;
	private int skipLevelCounter = 0;
	private int takeHelpCounter = 0;
	
	public GameAccessControl(GameLevelManager gameAccessControl) {
		this.gameAccessControl = gameAccessControl;
	}
	
	@Override
	public void increaseModifier() {
		increaseModifierCounter++;
		
		if (increaseModifierCounter > 1 && !Constants.FULL_ACCESS) {
			System.out.println("Buy full game in order to get full access.");
			return;
		}
		
		System.out.println(increaseModifierCounter);
		gameAccessControl.increaseModifier();
	}

	@Override
	public void skipLevel() {
		skipLevelCounter++;
		System.out.println(skipLevelCounter);
		gameAccessControl.skipLevel();
	}

	@Override
	public void takeHelp() {
		takeHelpCounter++;
		System.out.println(takeHelpCounter);
		gameAccessControl.takeHelp();
	}

}

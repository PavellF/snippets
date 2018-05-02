package theProxy.smart;

public class Constants {

	public static boolean FULL_ACCESS;
	
	public static void main(String...strings) {
		
		FULL_ACCESS = false;
		
		GameLevelManager gm = new GameAccessControl(new GameLevelManagerImpl());
		
		gm.increaseModifier();
		gm.increaseModifier();
		
		gm.takeHelp();
		
		gm.skipLevel();
		gm.skipLevel();
		gm.skipLevel();
		
	}
	
}

package theFlyweight;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class SimplePlayerFactory {

	private final static Map<String, PlayerType> TYPES = new HashMap<>();
	
	public static PlayerType getPlayerType(Color color, String role) {
		
		PlayerType pt = TYPES.get(role);
		
		if (pt == null) {
			pt = new PlayerType(color, role);
			TYPES.put(role, pt);
		}
		
		return pt;
	}
	
}

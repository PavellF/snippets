package theFlyweight;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameBoard extends JFrame {

	private List<Player> players = new ArrayList<>();
	
	public void setUpPlayer(int x, int y, String name, Color color, String role) {
		
		final PlayerType pt = SimplePlayerFactory.getPlayerType(color, role);
		final Player player = new Player(x, y, pt, name);
		players.add(player);
		
	}
	
	@Override
    public void paint(Graphics graphics) {
    		
    		players.forEach((val) -> {
    			val.draw(graphics);
    		});
    	
    }
	
}

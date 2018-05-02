package theFlyweight;

import java.awt.Color;
import java.awt.Graphics;

public class PlayerType {

	private final Color color;
	private final String role;
	
	public PlayerType(Color color, String role) {
		super();
		this.color = color;
		this.role = role;
	}

	public void draw(Graphics g, int x, int y) {
        
		g.setColor(color);
        g.fillOval(x - 5, y - 10, 10, 10);
    }

	@Override
	public String toString() {
		return "PlayerType [color=" + color + ", role=" + role + "]";
	}

	
}

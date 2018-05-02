package theFlyweight;

import java.awt.Graphics;

public class Player {

	private int x;
	private int y;
	private final String name;
	private final PlayerType playerType;
	
	public Player(int x, int y, PlayerType playerType, String name) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.playerType = playerType;
	}
	
	public void draw(Graphics g) {
		playerType.draw(g, x, y);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Player [x=" + x + ", y=" + y + ", name=" + name + ", playerType=" + playerType + "]";
	}
	
}

package theFlyweight;

import java.awt.Color;

public class Client {

	public static void main(String[] args) {
		
		GameBoard gb = new GameBoard();
		int canvasSize = 500;
		
		for (int i = 0; i < 1000; i++) {
			gb.setUpPlayer(
					random(0, canvasSize), 
					random(0, canvasSize), 
					"player"+random(0, 1000), 
					Color.CYAN, "regular");
			gb.setUpPlayer(
					random(0, canvasSize), 
					random(0, canvasSize), 
					"player"+random(0, 1000), 
					Color.MAGENTA, "advanced");
		}
		
		gb.setSize(canvasSize, canvasSize);
		gb.setVisible(true);
		
	}
	
	private static int random(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
	
}

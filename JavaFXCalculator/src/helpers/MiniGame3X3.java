package helpers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MiniGame3X3 implements EventHandler<ActionEvent> {

	private byte turns;
	private int playerScore;
	private int jvmScore;
	private int tries;
    private Button[] gameButtons;
    private TextField scoreField;
   
	public MiniGame3X3(Button[] gameButtons, int playerScore, int jvmScore, int tries, TextField scoreField){
		this.setGameButtons(gameButtons);
		this.playerScore = playerScore;
		this.jvmScore = jvmScore;
		this.tries = tries;
		this.scoreField = scoreField;
	}
	
	public MiniGame3X3(Button[] gameButtons){
		this.setGameButtons(gameButtons);
		this.playerScore = 0;
		this.jvmScore = 0;
		this.tries = 0;
		this.scoreField = new TextField();
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		for(int o = 0; o<gameButtons.length; o++){
			if(event.getSource() == gameButtons[o]){
				gameButtons[o].setText("0");
				gameButtons[o].setDisable(true);
			}
		  }
		
		turns++;
		
		if(turns <= 4){ 
			this.setMultiplicationSign();
		}
		 this.getWinner();
	}
	
	private void setMultiplicationSign(){
		
		boolean success = false;
		
		while(success != true){
			int fieldN = HelpMethods.getRandomInt(1, 9)-1;
			
			if(gameButtons[fieldN].getText().equals("")){
				gameButtons[fieldN].setText("×");
				gameButtons[fieldN].setDisable(true);
				success = true;
			}
		}
	}
	
	private boolean getScore(String equal){
		
		for(int h = 0; h<9; h+=3){ //h - horizontal
			
			if((gameButtons[0+h].getText()+gameButtons[1+h].getText()
					+gameButtons[2+h].getText()).equals(equal)) return true;
			
		}
		
		for(int v = 0; v<3;v++){ //v - vertical
			
			if((gameButtons[0+v].getText()+gameButtons[3+v].getText()
					+gameButtons[6+v].getText()).equals(equal)) return true;
			
		}
		
        for(int d = 0; d<=2; d+=2){ //d - diagonal
			
			if((gameButtons[2-d].getText()+gameButtons[4].getText()
					+gameButtons[6+d].getText()).equals(equal)) return true;
			
		}
		
		return false;
	}
	
	private void getWinner(){
		
		if(this.getScore("000")){
			playerScore++;
			this.setScore();
		}else if(this.getScore("×××")){
			jvmScore++;
			this.setScore();
		}else if(turns == 4){
			this.setScore();
		}
	}
	
	private void setScore(){
		this.clearField();
		turns = 0;
		tries++;
		scoreField.setText("You wins: "+this.getPlayerScore()+" Random wins: "+this.getJVMScore()+
				" Games played: "+this.getTries());
	}
	
	private void clearField(){
		
		for(int i = 0; i<gameButtons.length; i++){
			gameButtons[i].setText("");
			gameButtons[i].setDisable(false);
		}
	}
	
	private void setGameButtons(Button[] gameButtons){
		if(Math.sqrt(gameButtons.length) == 3)
		   this.gameButtons = gameButtons;
		else throw new IllegalArgumentException("array size schould be equal 9");
	}
	
	public int getPlayerScore(){
		return playerScore;
	}
	
	public int getJVMScore(){
		return jvmScore;
	}
	
	public int getTries(){
		return tries;
	}
	
}

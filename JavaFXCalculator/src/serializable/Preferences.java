package serializable;

import java.io.Serializable;

public class Preferences implements Serializable {

	private static final long serialVersionUID = 1L;
	private int hours;
	private int minutes;
	private int days;
	private boolean cbShowTime;
	private boolean cbShowHelp;
	private String style;
	private int playerScore;
	private int jvmScore;
	private int tries;
	
	public Preferences(int hours, int minutes, int days, boolean cbShowTime, boolean cbShowHelp, String style, 
			int playerScore, int jvmScore, int tries){
		
		this.hours = hours;
		this.minutes = minutes;
		this.days = days;
		this.cbShowHelp = cbShowHelp;
		this.cbShowTime = cbShowTime;
		this.style = style;
		this.playerScore = playerScore;
		this.jvmScore = jvmScore;
		this.tries = tries;
	}
	
	public int getHours(){
		return this.hours;
	}
	
	public int getMinutes(){
		return this.minutes;
	}
	
	public int getDays(){
		return this.days;
	}
	
	public boolean getShowTimeValue(){
		return this.cbShowTime;
	}
	
	public boolean getShowHelpValue(){
		return this.cbShowHelp;
	}
	
	public String getStyleValue(){
		return this.style;
	}
	
	public int getPlayerScore(){
		return this.playerScore;
	}
	
	public int getJVMScore(){
		return this.jvmScore;
	}
	
	public int getTries(){
		return this.tries;
	}
}

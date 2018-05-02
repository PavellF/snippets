package applicationGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.control.TextField;

public class Chronometer {

	private int hours;
	private int minutes;
	private int days;
	private int startHours;
	private int startMinutes;
	private int startDays;
	private boolean timerKill = false;
	private List<TextField> fields;
	private List<String> texts;
	
	
	public Chronometer(){
		fields = new ArrayList<TextField>();
		texts = new ArrayList<String>();
	}
	
	public void setStartValues(int startdays, int startHours, int startMinutes){
		this.days = startdays;
		this.startHours = startHours;
		this.startMinutes = startMinutes;
	}
	
	public void addSet(TextField field, String text){
		fields.add(field);
		texts.add(text);
	}
	
	public void getTime(){
    	Timer timer = new Timer();
    	
    	for(int i = 0; i<texts.size(); i++){
    		fields.get(i).setText(parseString(texts.get(i)));
    	}
    	
    	TimerTask minutesTask = new TimerTask(){

			public void run() {
				minutes++;
				startMinutes++;
				
				if(startMinutes == 60){
					startMinutes = 0;
					startHours++;
				}
				
				if(minutes == 60){
					minutes = 0;
					hours++;
				}
				
				if(hours == 24){
					hours = 0;
					days++;
				}
				
				if(startHours == 24){
					startHours = 0;
					startDays++;
				}
				
				for(int i = 0; i<texts.size(); i++){
		    		fields.get(i).setText(parseString(texts.get(i)));
		    	}
				
				if(timerKill == true) timer.cancel();
			}
		}; 
		timer.scheduleAtFixedRate(minutesTask, 60000, 60000);
	}
	
	private synchronized String parseString(String string){
		String value = string;
		value = value.replaceAll("%minutes", ""+minutes);
		value = value.replaceAll("%hours", ""+hours);
		value = value.replaceAll("%days", ""+days);
		value = value.replaceAll("%startminutes", ""+startMinutes);
		value = value.replaceAll("%starthours", ""+startHours);
		value = value.replaceAll("%startdays", ""+startDays);
			
		return value;
	}
	
	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getOverallHours() {
		return startHours;
	}

	public void setOverallHours(int overallHours) {
		this.startHours = overallHours;
	}

	public int getOverallMinutes() {
		return startMinutes;
	}

	public void setOverallMinutes(int overallMinutes) {
		this.startMinutes = overallMinutes;
	}
	
	public void killTimer(){
		this.timerKill = true;
	}
	
}

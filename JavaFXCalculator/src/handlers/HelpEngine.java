package handlers;

import applicationGUI.HelpGUI;
import helpers.HelpMethods;
import helpers.SaveManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import serializable.CurrentConstant;

public class HelpEngine implements EventHandler<ActionEvent>{

	private HelpGUI gui;
	private Button[] constantButtons;
	private DataExchanger<Boolean> dataEx;
	
	public HelpEngine(HelpGUI gui, Button[] constantButtons, DataExchanger<Boolean> dataEx){
		this.gui = gui;
		this.constantButtons = constantButtons;
		this.dataEx = dataEx;
	}
	
	private void save(){
		
		for(int o = 0; o<SaveManager.instance().getValues().length; o++){
			
            if(gui.getButtonSelector().getValue().equals(constantButtons[o].getText()) 
            		&& HelpMethods.thisStringIsDouble(gui.getSetValueField().getText())){
				
            	gui.getSavebtn().setText("Save");
            	SaveManager.instance().getValues()[o] = new CurrentConstant(gui.getSymbolChoice().getValue(), gui.getSetValueField().getText());
			    constantButtons[o].setText(SaveManager.instance().getValues()[o].getConstSign());
			    gui.getButtonSelector().getItems().remove(o);
			    gui.getButtonSelector().getItems().add(o, constantButtons[o].getText());
			    gui.getButtonSelector().setValue(constantButtons[o].getText());
			    SaveManager.instance().saveFile(SaveManager.instance().getValues(), System.getProperty("user.dir")+"/myValues.val");
			    break;
			}else if(!HelpMethods.thisStringIsDouble(gui.getSetValueField().getText())){
				gui.getSavebtn().setText("Invalid input");
				break;
			}
		}
	}
	
    public void visiblityOnHelpMessages(){
    	if(!gui.getVisiblityOnHelpMessages().isSelected()){
			gui.getVisiblityOnHelpMessages().setSelected(false);
			dataEx.addValue("Hide help messgaes", false);
		}else{
			gui.getVisiblityOnHelpMessages().setSelected(true);
			dataEx.addValue("Hide help messgaes", true);
		}
    }
	
    public void visibilityOnTimerField(){
    	if(!gui.getVisiblityOnTimerField().isSelected()){
    		gui.getVisiblityOnTimerField().setSelected(false);
    		dataEx.addValue("Show Timer", true);
    	}else if(gui.getVisiblityOnTimerField().isSelected()){
			gui.getVisiblityOnTimerField().setSelected(true);
			dataEx.addValue("Show Timer", false);
		}
    }
    
    @Override
	public void handle(ActionEvent event) {
		this.visiblityOnHelpMessages();
		this.visibilityOnTimerField();
		if(event.getSource() == gui.getSavebtn()) this.save();
	}
 }

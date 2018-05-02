package applicationGUI;

import helpers.MiniGame3X3;
import helpers.SaveManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class HelpGUI implements GUI<GridPane,GridPane>{

	final private TextArea infoArea = GuiElementConstructor.textAreaConstructor(SaveManager.instance().loadDescription(), true,true);
	final private TextField overallTimeField = GuiElementConstructor.textFieldConstructor(333, 1980, 64, 222, false, "");
	final private TextField setValueField = GuiElementConstructor.textFieldConstructor(22, 160, 16, 32, true, "For example 9.8");
	final private CheckBox visiblityOnTimerField = GuiElementConstructor.checkBoxConstructor("hide timer");
	final private CheckBox visiblityOnHelpMessages = GuiElementConstructor.checkBoxConstructor("hide help messages");
	final private TextField scoreField = GuiElementConstructor.textFieldConstructor(20, 600, -1, 22, false, "");
	final private Label styleLabel = GuiElementConstructor.labelConstructor("Select skin", 16, false);
	final private Label constantLabel = GuiElementConstructor.labelConstructor("Bind constant", 16, false);
	final private Label selectbtnLabel = GuiElementConstructor.labelConstructor("Select \nbutton:", 16, true);
	final private Label selectSymbolLabel = GuiElementConstructor.labelConstructor("Select \nsymbol:", 16, true);
	final private Label setValueLabel = GuiElementConstructor.labelConstructor("Set \nvalue:", 16, true);
	final private Button savebtn = GuiElementConstructor.buttonConstructor("Save", 38, 1080, 16, 40);
	final private ListView<String> styleList = new ListView<>();
	final private ChoiceBox<String> buttonSelector = new ChoiceBox<String>();
	final private ChoiceBox<String> symbolChoice = new ChoiceBox<String>();
	final private Button[] gameButtons = new Button[9]; 
	
	private MiniGame3X3 gameEngine;
	private Button[] constantButtons;
	
	public HelpGUI(Button[] constantButtons){
		SaveManager.instance().crateORloadConstValues();
		SaveManager.instance().createOrLoadPrefs();
		this.gameEngine = new MiniGame3X3(this.getGameButtons(),
				     SaveManager.instance().getPreferences().getPlayerScore(),
				     SaveManager.instance().getPreferences().getJVMScore(),
				     SaveManager.instance().getPreferences().getTries(),
				     this.scoreField);
		this.constantButtons = constantButtons;
	}
	
	public GridPane getGUI(EventHandler<ActionEvent> action, ControlPanel<GridPane> mainPane){
		
		GridPane root = GuiElementConstructor.gridConstructor(14, 4, 37, 240);
		GridPane infoPane = GuiElementConstructor.gridConstructor(1, 10, 173, 100);
		
		overallTimeField.setId("overallTime");
		
		infoArea.setMaxHeight(160);
		
		infoPane.add(infoArea, 1, 0, 4, 1);
		infoPane.add(overallTimeField, 5, 0, 6, 1);
		
		GridPane checkBoxPane = GuiElementConstructor.gridConstructor(4, 6, 30, 160);
		
		checkBoxPane.add(visiblityOnTimerField, 0, 1);
		visiblityOnTimerField.setSelected(SaveManager.instance().getPreferences().getShowTimeValue());
		GridPane.setHalignment(visiblityOnTimerField, HPos.CENTER);
		
		checkBoxPane.add(visiblityOnHelpMessages, 1, 1);
		visiblityOnHelpMessages.setSelected(SaveManager.instance().getPreferences().getShowHelpValue());
		GridPane.setHalignment(visiblityOnHelpMessages, HPos.CENTER);
		
		GridPane.setMargin(scoreField, new Insets(0,0,0,44));
		GridPane.setMargin(styleLabel, new Insets(0,0,0,40));
		GridPane.setMargin(constantLabel, new Insets(0,0,0,14));
		
		visiblityOnHelpMessages.setOnAction(action);
		visiblityOnTimerField.setOnAction(action);
		
		scoreField.setText("You wins: "+gameEngine.getPlayerScore()+" Random wins: "+gameEngine.getJVMScore()+
					" Games played: "+gameEngine.getTries());
		
		scoreField.setId("infoField");
		
		checkBoxPane.add(new Separator(), 0, 3, 6, 1);
		checkBoxPane.add(scoreField, 0, 4, 3, 1);
		checkBoxPane.add(styleLabel, 3, 4, 1, 1);
		checkBoxPane.add(constantLabel, 5, 4, 1, 1);
		
		GridPane preferencesPane = GuiElementConstructor.gridConstructor(4, 19, 30, 50);
		
		GridPane multiplyNzero = GuiElementConstructor.gridConstructor(3, 3, 66, 82);
		multiplyNzero.setId("multiplyNzero");
		
		for(int i = 0; i<gameButtons.length;i++){
			gameButtons[i] = GuiElementConstructor.buttonConstructor("", 64, 82, 26, 600);
			gameButtons[i].setOnAction(gameEngine);
			gameButtons[i].setId("GameButtons");
		}
		
		multiplyNzero.add(gameButtons[0], 0, 0);
		multiplyNzero.add(gameButtons[1], 1, 0);
		multiplyNzero.add(gameButtons[2], 2, 0);
		multiplyNzero.add(gameButtons[3], 0, 1);
		multiplyNzero.add(gameButtons[4], 1, 1);
		multiplyNzero.add(gameButtons[5], 2, 1);
		multiplyNzero.add(gameButtons[6], 0, 2);
		multiplyNzero.add(gameButtons[7], 1, 2);
		multiplyNzero.add(gameButtons[8], 2, 2);
		
		styleList.getItems().addAll("White","Blue","Black","Dark","Red");
		styleList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		styleList.getSelectionModel().select(SaveManager.instance().getPreferences().getStyleValue());
		
		for(int i = 0; i < constantButtons.length; i++){
			buttonSelector.getItems().add(constantButtons[i].getText());
		}
		
		buttonSelector.setMinWidth(160);
		buttonSelector.setValue(constantButtons[0].getText());
		
		symbolChoice.getItems().addAll("A","B","C","D","E","F","G","H","I","G","K","L","N","O","P","Q","R","S"
				,"T","U","V","W","X","Y","Z","Ω","μ","Є","Ф","Ѳ","∂");
		symbolChoice.setMinWidth(160);
		symbolChoice.setValue("A");
		
		savebtn.setOnAction(action);
		
		preferencesPane.add(multiplyNzero, 1, 0, 5, 4);
		preferencesPane.add(styleList, 7, 0, 5, 4);
		preferencesPane.add(selectbtnLabel, 13, 0, 2, 1);
		preferencesPane.add(selectSymbolLabel, 13, 1, 2, 1);
		preferencesPane.add(setValueLabel, 13, 2, 2, 1);
		preferencesPane.add(savebtn, 13, 3, 5, 1);
		preferencesPane.add(buttonSelector, 15, 0, 3, 1);
		preferencesPane.add(symbolChoice, 15, 1, 3, 1);
		preferencesPane.add(setValueField, 15, 2, 3, 1);
		
		root.add(mainPane.getMainPanel(), 0, 0, 4, 1);
		root.add(infoPane, 0, 2, 4, 5);
		root.add(checkBoxPane, 0, 7, 4, 2);
		root.add(preferencesPane, 0, 9, 4, 5);
		
		return root;
	}
	
	public MiniGame3X3 getMiniGameEngine(){
		return this.gameEngine;
	}
	
	public TextArea getInfoArea() {
		return infoArea;
	}

	public TextField getOverallTimeField() {
		return overallTimeField;
	}

	public TextField getSetValueField() {
		return setValueField;
	}

	public CheckBox getVisiblityOnTimerField() {
		return visiblityOnTimerField;
	}

	public CheckBox getVisiblityOnHelpMessages() {
		return visiblityOnHelpMessages;
	}

	public TextField getScoreField() {
		return scoreField;
	}

	public Label getStyleLabel() {
		return styleLabel;
	}

	public Label getConstantLabel() {
		return constantLabel;
	}

	public Label getSelectbtnLabel() {
		return selectbtnLabel;
	}

	public Label getSelectSymbolLabel() {
		return selectSymbolLabel;
	}

	public Label getSetValueLabel() {
		return setValueLabel;
	}

	public Button getSavebtn() {
		return savebtn;
	}

	public ListView<String> getStyleList() {
		return styleList;
	}

	public ChoiceBox<String> getButtonSelector() {
		return buttonSelector;
	}

	public ChoiceBox<String> getSymbolChoice() {
		return symbolChoice;
	}

	public Button[] getGameButtons() {
		return gameButtons;
	}
	
}

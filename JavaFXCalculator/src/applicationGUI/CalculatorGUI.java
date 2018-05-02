package applicationGUI;

import helpers.SaveManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class CalculatorGUI implements GUI<GridPane,GridPane>{
	
	final private Button plusbtn = GuiElementConstructor.buttonConstructor("+", 40, 96, 18, 0);
	final private Button minusbtn = GuiElementConstructor.buttonConstructor("-", 40, 96, 18, 0);
	final private Button multiplybtn = GuiElementConstructor.buttonConstructor("×", 40, 96, 18, 0);
	final private Button dividebtn = GuiElementConstructor.buttonConstructor("÷", 40, 96, 18, 0);
	final private Button percentbtn = GuiElementConstructor.buttonConstructor("%", 33, 70, 16, 36);
	final private Button rightbrbtn = GuiElementConstructor.buttonConstructor(")", 33, 70, 16, 36);
	final private Button undobtn = GuiElementConstructor.buttonConstructor("<-", 33, 70, 16, 36);
	final private Button equalbtn = GuiElementConstructor.buttonConstructor("=", 40, 70, 16, 0);
	final private Button delbtn = GuiElementConstructor.buttonConstructor("DEL", 40, 70, 16, 0);
	final private Button ansbtn = GuiElementConstructor.buttonConstructor("ANS", 40, 70, 16, 0);
	final private Button sqrtbtn = GuiElementConstructor.buttonConstructor("√", 33, 70, 16, 36);
	final private Button logXbtn = GuiElementConstructor.buttonConstructor("logX", 33, 70, 10, 36);
	final private Button leftbrbtn = GuiElementConstructor.buttonConstructor("(", 33, 70, 16, 36);
	final private Button dotbtn = GuiElementConstructor.buttonConstructor(".", 40, 70, 16, 0);
	final private Button remainderbtn = GuiElementConstructor.buttonConstructor("Rem", 33, 70, 10, 36);
	final private Button roundbtn  = GuiElementConstructor.buttonConstructor("Round", 33, 70, 10, 36);
	final private Button revbtn  = GuiElementConstructor.buttonConstructor("1/x", 33, 70, 10, 36);
	final private Button cosbtn  = GuiElementConstructor.buttonConstructor("cos", 40, 70, 16, 0);
	final private Button coshbtn  = GuiElementConstructor.buttonConstructor("cosh", 40, 70, 16, 0);
	final private Button sinhbtn  = GuiElementConstructor.buttonConstructor("sinh", 40, 70, 16, 0);
	final private Button tanhbtn  = GuiElementConstructor.buttonConstructor("tanh", 40, 70, 16, 0);
	final private Button sinbtn  = GuiElementConstructor.buttonConstructor("sin", 40, 70, 16, 0);
	final private Button tgbtn  = GuiElementConstructor.buttonConstructor("tan", 40, 70, 16, 0);
	final private Button logEbtn  = GuiElementConstructor.buttonConstructor("ln", 40, 70, 16, 0);
	final private Button secDegreebtn  = GuiElementConstructor.buttonConstructor("x²", 40, 70, 16, 0);
	final private Button degreebtn  = GuiElementConstructor.buttonConstructor("xª", 40, 70, 16, 0);
	final private Button rootXbtn  = GuiElementConstructor.buttonConstructor("ª√", 40, 70, 16, 0);
	final private Button randombtn  = GuiElementConstructor.buttonConstructor("Random", 40, 70, 10, 0);
	final private Button log10btn  = GuiElementConstructor.buttonConstructor("lg", 40, 70, 16, 0);
	final private Button pibtn = GuiElementConstructor.buttonConstructor("π", 40, 70, 16, 0);
	final private Button ebtn = GuiElementConstructor.buttonConstructor("e", 40, 70, 16, 0);
	final private Button punctuationMarkbtn = GuiElementConstructor.buttonConstructor(",", 33, 70, 16, 36);
	final private ToggleButton hypbtn = new ToggleButton("hyp");
	final private Button[] digitalButtons = new Button[10];
	final private Button[] constantButtons = new Button[6];
	final private ChoiceBox<String> converterChoice = new ChoiceBox<String>();
	final private Label consoleLabel = GuiElementConstructor.labelConstructor("Console:", 16, false);
	final private Label historyLabel = GuiElementConstructor.labelConstructor("History:", 16, false);
	final private TextField calcDisplay = GuiElementConstructor.textFieldConstructor(60, 1080, 39, 66, false, "");
	final private TextField timefield = GuiElementConstructor.textFieldConstructor(20, 160, -1, 22, false, "hui");
	final private TextField entermin = GuiElementConstructor.textFieldConstructor(22, 70, -1, 26, true, "min");
	final private TextField entermax = GuiElementConstructor.textFieldConstructor(22, 70, -1, 26, true, "max");
	final private TextArea historyDisplay = GuiElementConstructor.textAreaConstructor("", false,true);
	final private TextArea console = GuiElementConstructor.textAreaConstructor("", false,true);
	
	public CalculatorGUI(){
		SaveManager.instance().crateORloadConstValues();
		SaveManager.instance().createOrLoadPrefs();
	}
	
	public GridPane getGUI(EventHandler<ActionEvent> action, ControlPanel<GridPane> mainPane){
		
		GridPane root = GuiElementConstructor.gridConstructor(14, 4, 37, 240);
		 
		 root.setScaleX(1);
	     root.setScaleY(1);
		  
		  //main text area
	      
	      GridPane.setValignment(calcDisplay, VPos.TOP);
	      calcDisplay.setAlignment(Pos.CENTER_RIGHT);
	      calcDisplay.setId("calcDisplay");
	      
	      //operations
	      
	      GridPane operPane = GuiElementConstructor.gridConstructor(5, 3, 30, 100);
	     
	      plusbtn.setOnAction(action);
	      GridPane.setHalignment(plusbtn, HPos.CENTER);
	      
	      minusbtn.setOnAction(action);
	      GridPane.setHalignment(minusbtn, HPos.CENTER);
	      
	      multiplybtn.setOnAction(action);
	      GridPane.setHalignment(multiplybtn, HPos.CENTER);
	      
	      dividebtn.setOnAction(action);
	      GridPane.setHalignment(dividebtn, HPos.CENTER);
	     
	      percentbtn.setOnAction(action);
	      GridPane.setHalignment(percentbtn, HPos.CENTER);
	      
	      rightbrbtn.setOnAction(action);
	      GridPane.setHalignment(rightbrbtn, HPos.LEFT);
	      
	      undobtn.setOnAction(action);
	      GridPane.setHalignment(undobtn, HPos.CENTER);
	      
	      equalbtn.setOnAction(action);
	      delbtn.setOnAction(action);
	      ansbtn.setOnAction(action);
	      
	      operPane.add(percentbtn, 1, 0);
	      operPane.add(rightbrbtn, 0, 0);
	      operPane.add(undobtn, 2, 0);
	      operPane.add(multiplybtn, 0, 1, 2, 1);
	      operPane.add(dividebtn, 0, 2, 2, 1);
	      operPane.add(minusbtn, 0, 3, 2, 1);
	      operPane.add(plusbtn, 0, 4, 2, 1);
	      operPane.add(delbtn, 2, 2);
	      operPane.add(ansbtn, 2, 3);
	      operPane.add(equalbtn, 2, 4);
	      
	      //numbers
	      
	      GridPane numbersPane = GuiElementConstructor.gridConstructor(5, 3, 30, 100);
	      
	      sqrtbtn.setOnAction(action);
	      logXbtn.setOnAction(action);
	      leftbrbtn.setOnAction(action);
	      
	      dotbtn.setOnAction(action);
	      dotbtn.setAlignment(Pos.BOTTOM_CENTER);
	      
	      for(int i = 0; i<10; i++){
	    	  if(i == 0){
	    		  digitalButtons[i] = GuiElementConstructor.buttonConstructor(""+i, 40, 152, 16, 0);
	    		  digitalButtons[i].setOnAction(action);
	    		  continue;
	    	  }
	    	  digitalButtons[i] = GuiElementConstructor.buttonConstructor(""+i, 40, 70, 16, 0);
	    	  digitalButtons[i].setOnAction(action);
	    	}
	      
	      numbersPane.add(sqrtbtn, 0, 0);
	      numbersPane.add(logXbtn, 1, 0);
	      numbersPane.add(leftbrbtn, 2, 0);
	      numbersPane.add(dotbtn, 2, 4);
	      numbersPane.add(digitalButtons[0], 0, 4, 2, 1);
	      numbersPane.add(digitalButtons[1], 0, 3);
	      numbersPane.add(digitalButtons[2], 1, 3);
	      numbersPane.add(digitalButtons[3], 2, 3);
	      numbersPane.add(digitalButtons[4], 0, 2);
	      numbersPane.add(digitalButtons[5], 1, 2);
	      numbersPane.add(digitalButtons[6], 2, 2);
	      numbersPane.add(digitalButtons[7], 0, 1);
	      numbersPane.add(digitalButtons[8], 1, 1);
	      numbersPane.add(digitalButtons[9], 2, 1);
	     
	      //trigonometry
	      
	      GridPane trignPane = GuiElementConstructor.gridConstructor(5, 3, 30, 100);
	      
	      hypbtn.setMinHeight(40);
	      hypbtn.setPrefWidth(70);
	      hypbtn.setFont(Font.font("PT Sans", 16));
	      hypbtn.setId("toggleButton");
	      
	      sinhbtn.setVisible(false);
	      coshbtn.setVisible(false);
	      tanhbtn.setVisible(false);
	      
	      remainderbtn.setOnAction(action);
	      roundbtn.setOnAction(action);
	      cosbtn.setOnAction(action);
	      sinbtn.setOnAction(action);
	      tgbtn.setOnAction(action);
	      logEbtn.setOnAction(action);
	      secDegreebtn.setOnAction(action);
	      rootXbtn.setOnAction(action);
	      randombtn.setOnAction(action);
	      log10btn.setOnAction(action);
	      degreebtn.setOnAction(action);
	      revbtn.setOnAction(action);
	      coshbtn.setOnAction(action);
	      sinhbtn.setOnAction(action);
	      tanhbtn.setOnAction(action);
	      hypbtn.setOnAction(e -> {
	    	  if(hypbtn.isSelected()){
	          	sinbtn.setVisible(false);
	          	cosbtn.setVisible(false);
	          	tgbtn.setVisible(false);
	          	sinhbtn.setVisible(true);
	          	coshbtn.setVisible(true);
	          	tanhbtn.setVisible(true);
	  			
	  		}else if(!hypbtn.isSelected()){
	  			sinbtn.setVisible(true);
	  			cosbtn.setVisible(true);
	  			tgbtn.setVisible(true);
	  			sinhbtn.setVisible(false);
	  			coshbtn.setVisible(false);
	  			tanhbtn.setVisible(false);
	  		}
	      });
	      
	      trignPane.add(remainderbtn, 1, 0);
	      trignPane.add(roundbtn, 2, 0);
	      trignPane.add(hypbtn, 0, 1);
	      trignPane.add(revbtn, 0, 0);
	      trignPane.add(cosbtn, 1, 1);
	      trignPane.add(coshbtn, 1, 1);
	      trignPane.add(sinbtn, 1, 3);
	      trignPane.add(sinhbtn, 1, 3);
	      trignPane.add(tgbtn, 1, 2);
	      trignPane.add(tanhbtn, 1, 2);
	      trignPane.add(logEbtn, 2, 1);
	      trignPane.add(secDegreebtn, 2, 2);
	      trignPane.add(rootXbtn, 2, 3);
	      trignPane.add(randombtn, 0, 4);
	      trignPane.add(log10btn, 1, 4);
	      trignPane.add(degreebtn, 2, 4);
	      trignPane.add(entermin, 0, 2);
	      trignPane.add(entermax, 0, 3);
	      
	      //consatant Panel
	      
	      GridPane constPanel = GuiElementConstructor.gridConstructor(5, 3, 30, 100);
	      
	      for(int b = 0; b<constantButtons.length; b++){
	    	  constantButtons[b] = GuiElementConstructor.buttonConstructor(SaveManager.instance().getValues()[b].getConstSign(), 40, 70, 16, 0);
	    	  constantButtons[b].setOnAction(action);
	      }
	      
	      pibtn.setOnAction(action);
	      ebtn.setOnAction(action);
	      punctuationMarkbtn.setOnAction(action);
	      
	      converterChoice.getItems().addAll("Rad","Deg");
	      converterChoice.setPrefWidth(70);
	      converterChoice.setValue("Rad");
	      
	      constPanel.add(constantButtons[0], 1, 1);
	      constPanel.add(constantButtons[1], 1, 2);
	      constPanel.add(constantButtons[2], 1, 3);
	      constPanel.add(constantButtons[3], 1, 4);
	      constPanel.add(pibtn, 2, 1);
	      constPanel.add(ebtn, 2, 2);
	      constPanel.add(constantButtons[4], 2, 3);
	      constPanel.add(constantButtons[5], 2, 4);
	      constPanel.add(converterChoice, 1, 0);
	      constPanel.add(punctuationMarkbtn, 2, 0);
	      
	      //history, console, time field
	      
	      GridPane.setValignment(timefield, VPos.BOTTOM);
	      GridPane.setHalignment(timefield, HPos.RIGHT);
	     
	      timefield.setId("infoField");
	      
	      GridPane.setValignment(historyLabel, VPos.BOTTOM);
	      GridPane.setMargin(historyLabel, new Insets(0,0,0,10));
	      GridPane.setValignment(consoleLabel, VPos.BOTTOM);
	      GridPane.setMargin(consoleLabel, new Insets(0,0,0,10));
	      
	      root.add(mainPane.getMainPanel(), 0, 0, 4, 1);
	      root.add(calcDisplay, 0, 1, 4, 2);
	      root.add(operPane, 3, 3, 1, 6);
	      root.add(numbersPane, 2, 3, 1, 6);
	      root.add(trignPane, 1, 3, 1, 6);
	      root.add(consoleLabel, 0, 9);
	      root.add(historyLabel, 2, 9);
	      root.add(console, 0, 10, 2, 4);
	      root.add(historyDisplay, 2, 10, 2, 4);
	      root.add(timefield, 3, 9);
	      root.add(constPanel, 0, 3, 1, 6);
		
		return root;
	}

    
	
	public Button getPlusbtn() {
		return plusbtn;
	}


	public Button getMinusbtn() {
		return minusbtn;
	}


	public Button getMultiplybtn() {
		return multiplybtn;
	}


	public Button getDividebtn() {
		return dividebtn;
	}


	public Button getPercentbtn() {
		return percentbtn;
	}


	public Button getRightbrbtn() {
		return rightbrbtn;
	}


	public Button getUndobtn() {
		return undobtn;
	}


	public Button getEqualbtn() {
		return equalbtn;
	}


	public Button getDelbtn() {
		return delbtn;
	}


	public Button getAnsbtn() {
		return ansbtn;
	}


	public Button getSqrtbtn() {
		return sqrtbtn;
	}


	public Button getLogXbtn() {
		return logXbtn;
	}


	public Button getLeftbrbtn() {
		return leftbrbtn;
	}


	public Button getDotbtn() {
		return dotbtn;
	}


	public Button getRemainderbtn() {
		return remainderbtn;
	}


	public Button getRoundbtn() {
		return roundbtn;
	}


	public Button getRevbtn() {
		return revbtn;
	}


	public Button getCosbtn() {
		return cosbtn;
	}


	public Button getCoshbtn() {
		return coshbtn;
	}


	public Button getSinhbtn() {
		return sinhbtn;
	}


	public Button getTanhbtn() {
		return tanhbtn;
	}


	public Button getSinbtn() {
		return sinbtn;
	}


	public Button getTgbtn() {
		return tgbtn;
	}


	public Button getLogEbtn() {
		return logEbtn;
	}


	public Button getSecDegreebtn() {
		return secDegreebtn;
	}


	public Button getRootXbtn() {
		return rootXbtn;
	}


	public Button getRandombtn() {
		return randombtn;
	}


	public Button getLog10btn() {
		return log10btn;
	}


	public Button getPibtn() {
		return pibtn;
	}


	public Button getEbtn() {
		return ebtn;
	}


	public Button getPunctuationMarkbtn() {
		return punctuationMarkbtn;
	}


	public ToggleButton getHypbtn() {
		return hypbtn;
	}


	public Button[] getDigitalButtons() {
		return digitalButtons;
	}


	public Button[] getConstantButtons() {
		return constantButtons;
	}


	public ChoiceBox<String> getConverterChoice() {
		return converterChoice;
	}


	public Label getConsoleLabel() {
		return consoleLabel;
	}


	public Label getHistoryLabel() {
		return historyLabel;
	}


	public TextField getCalcDisplay() {
		return calcDisplay;
	}


	public TextField getTimefield() {
		return timefield;
	}


	public TextField getEntermin() {
		return entermin;
	}


	public TextField getEntermax() {
		return entermax;
	}


	public TextArea getHistoryDisplay() {
		return historyDisplay;
	}


	public TextArea getConsole() {
		return console;
	}
}

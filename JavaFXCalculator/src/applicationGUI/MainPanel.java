package applicationGUI;

import application.Start;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;


public class MainPanel implements ControlPanel<GridPane>{

	public GridPane getMainPanel(){
		
		  GridPane topPane = GuiElementConstructor.gridConstructor(1, 6, 37, 145);
		  
		  Hyperlink helpLink = new Hyperlink("Help");
	      Hyperlink calcEnginelink = new Hyperlink("Calculator");
		  
		  topPane.setId("topPane");
	      topPane.setAlignment(Pos.TOP_LEFT);
	      
	      calcEnginelink.getStyleClass().add("panelLinks");
	      GridPane.setHalignment(calcEnginelink, HPos.CENTER);
	      
	      
	      helpLink.getStyleClass().add("panelLinks");
	      
	      calcEnginelink.setOnAction(event ->{
			Start.setScene(Start.getCalculatorScene());
	        Start.getCalcEngine().timerVisibility();
			 });
		      
		  helpLink.setOnAction(event ->{
			 Start.setScene(Start.getHelpScene());
			 
			});
	      
	      topPane.add(calcEnginelink,0,0);
	      topPane.add(helpLink,6,0);
		
		return topPane;
	}
}

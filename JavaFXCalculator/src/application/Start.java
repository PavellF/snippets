package application;
	
import applicationGUI.CalculatorGUI;
import applicationGUI.Chronometer;
import applicationGUI.ControlPanel;
import applicationGUI.HelpGUI;
import applicationGUI.MainPanel;
import handlers.CalcEngine;
import handlers.DataExchanger;
import handlers.HelpEngine;
import helpers.SaveManager;
import javafx.application.Application;
import javafx.stage.Stage;
import serializable.Preferences;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class Start extends Application{
	
	private static final CalculatorGUI calcgui = new CalculatorGUI();
	private static final DataExchanger<Boolean> dataEx = new DataExchanger<Boolean>();
	private static final CalcEngine calcHandler = new CalcEngine(calcgui, dataEx);
	private static final HelpGUI helpgui = new HelpGUI(calcgui.getConstantButtons());
	private static final ControlPanel<GridPane> mainPane = new MainPanel();
	private static final HelpEngine helpEngine = new HelpEngine(helpgui,calcgui.getConstantButtons(), dataEx);
	private static final Scene CalculatorScene = new Scene(calcgui.getGUI(calcHandler, mainPane));
	private static final Scene HelpScene = new Scene(helpgui.getGUI(helpEngine, mainPane));
	private static final Stage stage = new Stage(); 
   
	@Override
	public void start(Stage primaryStage){
		try {
			primaryStage = stage;
			
			helpEngine.visibilityOnTimerField();
			helpEngine.visiblityOnHelpMessages();
			calcHandler.timerVisibility();
			
			Chronometer applicationTimer = new Chronometer();
			
			applicationTimer.setStartValues(
					SaveManager.instance().getPreferences().getDays(), 
					SaveManager.instance().getPreferences().getHours(), 
					SaveManager.instance().getPreferences().getMinutes());
			applicationTimer.addSet(calcgui.getTimefield(), "Run time: %hours hours %minutes minutes.");
			applicationTimer.addSet(helpgui.getOverallTimeField(), "%startdaysd:%starthoursh:%startminutesm");
			applicationTimer.getTime();
			
			CalculatorScene.getStylesheets().add(getClass().getResource("styles/"+
			SaveManager.instance().getPreferences().getStyleValue()+".css").toExternalForm());
			
			HelpScene.getStylesheets().add(getClass().getResource("styles/"+
			SaveManager.instance().getPreferences().getStyleValue()+".css").toExternalForm());
			
			primaryStage.setScene(CalculatorScene);
			primaryStage.show();
			primaryStage.setTitle("JavaFX Calculator");
			primaryStage.setOnCloseRequest(e -> {
				
				SaveManager.instance().saveFile(new Preferences(
						applicationTimer.getOverallHours(), 
						applicationTimer.getOverallMinutes(), 
						applicationTimer.getDays(),
						helpgui.getVisiblityOnTimerField().isSelected(),
						helpgui.getVisiblityOnHelpMessages().isSelected(), 
						helpgui.getStyleList().getSelectionModel().getSelectedItem(),
						helpgui.getMiniGameEngine().getPlayerScore(),
						helpgui.getMiniGameEngine().getJVMScore(),
						helpgui.getMiniGameEngine().getTries()),
						System.getProperty("user.dir")+"/preferences.pre");
				applicationTimer.killTimer();
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static CalculatorGUI getCalculatorGUI(){
		return calcgui;
	}
	
	public static CalcEngine getCalcEngine(){
		return calcHandler;
	}
	
    public static Scene getCalculatorScene() {
		return CalculatorScene;
	}

    public static Scene getHelpScene() {
		return HelpScene;
	}
    
    public static void setScene(Scene scene){
    	stage.setScene(scene);
    }
    
    public static HelpEngine getHelpEngine(){
    	return helpEngine;
    }
    
    public static HelpGUI getHelpGUI(){
    	return helpgui;
    }
    
    public static DataExchanger<Boolean> getDataEx(){
    	return dataEx;
    }
    
    public static void main(String[] args) {launch(args);}
}

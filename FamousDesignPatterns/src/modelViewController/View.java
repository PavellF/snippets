package modelViewController;

import java.util.Date;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class View implements EventHandler<ActionEvent>, RemoteOrderObserver, OrderObserver {

	private final Button createOrderBtn = new Button("Create order");
	private final Button doRequestBtn = new Button("Do remote request");
	private final Button deleteOrderBtn = new Button("Delete order");
	private final Button updateAllBtn = new Button("Update all");
	
	final private TextField orderNameField = getTextField("Order name");
	final private TextField nameField = getTextField("Your name");
	final private TextField orderNameToDeleteField = getTextField("Name of deleted order");
	
	final private TextArea orderDisplay = new TextArea();
	
	final private ControllerInterface controllerInterface;
	
	public View(Stage primaryStage, ControllerInterface controllerInterface) {
		this.controllerInterface = controllerInterface;
		createView(primaryStage);
		
	}
	
	public void createView(Stage primaryStage) {
		
		GridPane root = getGrid(5, 3);
		 
		root.setScaleX(1);
	    root.setScaleY(1);
		  
	    createOrderBtn.setOnAction(this);
	    doRequestBtn.setOnAction(this);
	    deleteOrderBtn.setOnAction(this);
	    updateAllBtn.setOnAction(this);
	    
	    root.add(createOrderBtn, 0, 4);
	    root.add(orderNameField, 0, 3);
	    root.add(nameField, 0, 2);
	    root.add(deleteOrderBtn, 1, 3);
	    root.add(updateAllBtn, 1, 4);
	    root.add(orderNameToDeleteField, 1, 2);
	    root.add(doRequestBtn, 2, 2);
	    root.add(orderDisplay, 0, 0, 3, 2);
	    
	    primaryStage.setScene(new Scene(root));
	    
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		final Button button = (Button) event.getSource();
		
		switch (button.getText()) {
			
		case "Create order": handleNewOrder(); break;
		case "Do remote request": handleRemoteRequest(); break;
		case "Delete order": handleOrderDelete(); break;
		case "Update all" : handleUpdate(); break;
		
		}
		
	}
	
	private void handleUpdate() {
		controllerInterface.updateAll();
	}
	
	private void handleNewOrder() {
		
		final String name = orderNameField.getText();
		final String initiator = nameField.getText();
		
		final Order order = new Order(new Date(), name, initiator);
		
		controllerInterface.place(order);
	}
	
	private void handleOrderDelete() {
		final String innerText = orderNameToDeleteField.getText();
		controllerInterface.deleteOrder(innerText);
	}
	
	private void handleRemoteRequest() {
		controllerInterface.doRemoteRequest();
	}

	private TextField getTextField(String promptText){
		
	    TextField txtField = new TextField();
		txtField.setPromptText(promptText);
	    txtField.setEditable(true);
	      
		return txtField;
	}
	
	private GridPane getGrid(int rows, int columns){
		
		  GridPane pane = new GridPane();
	      pane.setAlignment(Pos.CENTER);
	      
	      ColumnConstraints[] colarray = new ColumnConstraints[columns];
	      RowConstraints[] rowarray = new RowConstraints[rows];
	      
	       for(int i = 0; i < columns; i++){
	    	  
	    	   	colarray[i] = new ColumnConstraints();
	    	   	colarray[i].setPrefWidth(250);
	    	   	pane.getColumnConstraints().add(colarray[i]);
	    	   	
	       }
	      
	      for(int i = 0; i < rows; i++){
	    	  
	    	  	rowarray[i] = new RowConstraints();
	    	  	rowarray[i].setPrefHeight(45);
	    	  	rowarray[i].setVgrow(Priority.SOMETIMES);
	    	  	rowarray[i].setValignment(VPos.CENTER);
	    	  	pane.getRowConstraints().add(rowarray[i]);
	    	  
	      }
	      
	      return pane;
	}

	@Override
	public void orderUpdated() {
		orderDisplay.appendText("SUCCESS\n");
		
	}

	@Override
	public void remoteOrderUpdated() {
		orderDisplay.appendText("SUCCESS\n");
	}
	
	public void rerenderAll(List<Order> orders) {
		
		orderDisplay.setText("");
		
		orders.forEach((val) -> {
			orderDisplay.appendText(val+"\n");
		});
		
	}
	
	public void renderAll(List<Order> orders) {
		
		orders.forEach((val) -> {
			orderDisplay.appendText(val+"\n");
		});
		
	}
	
}

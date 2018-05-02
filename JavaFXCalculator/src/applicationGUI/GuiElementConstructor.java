package applicationGUI;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

public class GuiElementConstructor {

	public static GridPane gridConstructor(int rows, int columns, int prefH, int prefW){
		
		  GridPane pane = new GridPane();
	      pane.setAlignment(Pos.CENTER);
	      
	      ColumnConstraints[] colarray = new ColumnConstraints[columns];
	      RowConstraints[] rowarray = new RowConstraints[rows];
	      
	       for(int i = 0; i < columns; i++){
	    	  
	    	  colarray[i] = new ColumnConstraints();
	    	  colarray[i].setPrefWidth(prefW);
	    	  
	    	  pane.getColumnConstraints().add(colarray[i]);
	       }
	      
	      for(int i = 0; i < rows; i++){
	    	  
	    	  rowarray[i] = new RowConstraints();
	    	  rowarray[i].setPrefHeight(prefH);
	    	  
	    	  rowarray[i].setVgrow(Priority.SOMETIMES);
	    	  rowarray[i].setValignment(VPos.CENTER);
	    	  pane.getRowConstraints().add(rowarray[i]);
	    	  
	      }
		return pane;
	}
	
	
	public static Button buttonConstructor(String text, int minHeight, int prefW, int fontSize, int maxHeight){

		  Button btn = new Button();
	      btn.setMinHeight(minHeight);
	      btn.setPrefWidth(prefW);
	      btn.setText(text);
	      btn.setFont(Font.font("PT Sans", fontSize));
	      
	      if(maxHeight > 0){
	    	  btn.setMaxHeight(maxHeight);
	      }
	      
	     return btn;
	}
	
	public static TextField textFieldConstructor(int prefH, int maxWidth, int fontSize, int minH, boolean editable, String promptText){
		
	    TextField txtField = new TextField();
		txtField.setPrefHeight(prefH);
		txtField.setMinHeight(minH);
		txtField.setPromptText(promptText);
	    txtField.setMaxWidth(maxWidth);
	    txtField.setFont(Font.font("PT Sans", fontSize));
		txtField.setEditable(editable);
	      
		return txtField;
	}
	
    public static Label labelConstructor(String content, int fontSize, boolean wrap){
		
    	Label label = new Label(content);
    	label.setFont(Font.font("PT Sans", fontSize));
    	label.setWrapText(wrap);
    	return label;
    }
    
    public static TextArea textAreaConstructor(String text, boolean editable,boolean wrap){
		
    	TextArea ta = new TextArea(text); 
    	ta.setEditable(editable);
    	ta.setWrapText(wrap);
    	
    	return ta;
    }
    
    public static CheckBox checkBoxConstructor(String content){
		
    	CheckBox chb = new CheckBox(content);
    	return chb;
    }
}

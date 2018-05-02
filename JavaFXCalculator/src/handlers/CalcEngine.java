package handlers;

import java.util.ArrayList;
import applicationGUI.CalculatorGUI;
import helpers.HelpMethods;
import helpers.RPN;
import helpers.SaveManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


public class CalcEngine implements EventHandler<ActionEvent>{

	private StringBuffer finalRpnString; 
	private String ans;
	private byte o,c;//o = open, c = close
	private ArrayList<String> inputListener; 
    private ArrayList<String> rpnStringDump;
    private ArrayList<String> operations;
	private ArrayList<String> historyDisplayLines;
    private ArrayList<String> consoleDisplayLines;
    private String[][] operationsDump;
    private CalculatorGUI gui;
    private DataExchanger<Boolean> DataEx;
    
    
    public CalcEngine(CalculatorGUI gui, DataExchanger<Boolean> dataEx){
    	this.gui = gui;
    	this.finalRpnString = new StringBuffer(120); 
    	this.ans = new String();
    	this.inputListener = new ArrayList<String>();
    	this.rpnStringDump = new ArrayList<String>();
    	this.operations = new ArrayList<String>();
    	this.historyDisplayLines = new ArrayList<String>();
    	this.consoleDisplayLines = new ArrayList<String>();
    	this.operationsDump =new String[12][3];
    	this.DataEx = dataEx;
    	}
    
    private void clearAll(String addTextToHistDisplay){
    	
    	if(!addTextToHistDisplay.isEmpty()) this.addLineToTextArea(gui.getHistoryDisplay(),historyDisplayLines, addTextToHistDisplay);
    	
    	finalRpnString.delete(0, 120);
		c=0;                  
		o=0;
		inputListener.clear();
	    rpnStringDump.clear();
	    operationsDump = HelpMethods.arrayPlaceholder(operationsDump, "");
	    operations.clear();
	    gui.getCalcDisplay().setText("");
	}
    
    private void operation(String operation,String containsFirst,String containsTwo){
    	
    	if(!operations.contains(" "+containsFirst) && !operations.contains(" "+containsTwo)){    //if this operation type minus (for example) uses fist time
			operations.add(" "+operation);//adds operation in operation array
			//adds empty place
			finalRpnString.append(' ');
		}else if(operations.contains(" "+containsFirst)){//if operation array contains plus 
			operations.set(operations.lastIndexOf(" "+containsFirst)," "+operation);//places minus instead plus  
			//then plus adds in to finalRpnString
			finalRpnString.append(" "+containsFirst+" ");
		}else if( operations.contains(" "+containsTwo)){                                          
			//done the same for minus (minus replaces minus) in example with minus
			operations.set(operations.lastIndexOf(" "+containsTwo)," "+operation);                                                     
			finalRpnString.append(" "+containsTwo+" ");
		}
    }
	
    private void undo(){
    	
    	try{
    		inputListener.remove(inputListener.size()-1);
    	}catch(ArrayIndexOutOfBoundsException e){
    		inputListener.clear();
    	}
    	
    	gui.getCalcDisplay().setText("");
		
    	inputListener.forEach((String val) -> gui.getCalcDisplay().appendText(val));
	}
    
    private void openingBrackets(){
    	    
    	    rpnStringDump.add(finalRpnString.toString());                                
			finalRpnString.delete(0, 120);                                                
			
			o++;
			
			if(!operations.isEmpty()){
				
				operations = HelpMethods.removeSpecificElementFromArrList(operations, " ");
				
				for(byte b = 0; b<operations.size(); b++){
					
					if(o == c+1){
						operationsDump[0][b] = operations.get(b);
					}else{
					    operationsDump[o][b] = operations.get(b);
					}
				}
				operations.clear();
			}
		}
    
	private void closingBrackets(){
    	
    	    boolean notEmpty = false;
    	
    	    finalRpnString = this.arrayReverse(operations, finalRpnString);
			
			String buffer = rpnStringDump.get(rpnStringDump.size()-1)+(RPN.getAnswer(finalRpnString.toString()));
    	    
    	    finalRpnString.delete(0, 120);
    	    
    	    finalRpnString.append(buffer);
			
			c++;
			
			operations.clear();           
			
			for(int r = operationsDump.length-1; r >= 0; r--){
					
		    		if(notEmpty == true) break;
					
					for(int c = 0; c<operationsDump[r].length; c++){
		    			
		    			if((!operationsDump[r][c].equals("")) && (r != 0)){
		    				
		    				operations.add(operationsDump[r][c]);
		    				operationsDump[r][c] = "";
		    				notEmpty = true;
		    			}else if((!operationsDump[0][c].equals("")) && (this.c == o) && (r == 0)){
		    				
		    				operations.add(operationsDump[0][c]);
		    				operationsDump[0][c] = "";
		    			}
		    		  }
		    	   }
			
			rpnStringDump.remove(rpnStringDump.size()-1);
		}
    
    private void random(){
    	
    	try{
			String result = ""+HelpMethods.getRandomInt(Integer.parseInt(
					gui.getEntermin().getText()), Integer.parseInt(gui.getEntermax().getText()));
			this.addLineToTextArea(gui.getConsole(), consoleDisplayLines, result);
			
			gui.getCalcDisplay().appendText(""+result);
			
		    for(int i = 0; i<result.length(); i++){
            	
            	inputListener.add(result.charAt(i)+"");
            }
        }catch(IllegalArgumentException iae){
			this.addLineToTextArea(gui.getConsole(), consoleDisplayLines,
			"Only integers allowed. Minimal value (min)\nmust be less than maximum value (max)");
    	}
    }
    
    private void roundThis(){
    	
    	if(HelpMethods.thisStringIsDouble(gui.getCalcDisplay().getText()) || HelpMethods.thisStringIsInteger(gui.getCalcDisplay().getText())){
    		String result = ""+Math.round(Double.parseDouble(gui.getCalcDisplay().getText()));
    		inputListener.clear();
    		
            for(int i = 0; i<result.length(); i++){
            	
            	inputListener.add(result.charAt(i)+"");
            	
            }
    		
            gui.getCalcDisplay().setText(result);
    		
    	}else{
    		this.addLineToTextArea(gui.getConsole(), consoleDisplayLines, "Fail. Only numbers.");
    	}
    }
    
    private void reverseThis(){
    	
    	if(HelpMethods.thisStringIsDouble(gui.getCalcDisplay().getText()) || HelpMethods.thisStringIsInteger(gui.getCalcDisplay().getText())){
    		
    		String result = 1/Double.parseDouble(gui.getCalcDisplay().getText())+"";
    		inputListener.clear();
    		
            for(int i = 0; i<result.length(); i++){
            	inputListener.add(result.charAt(i)+"");
            }
    		
            gui.getCalcDisplay().setText(result);
    		
    	}else{
    		this.addLineToTextArea(gui.getConsole(), consoleDisplayLines,"Fail. Only numbers.");
    	}
    }
    
   private StringBuffer arrayReverse(ArrayList<String> list, StringBuffer str){
    	
		for(int j = 0; j<list.size(); j++){
			try{
				
				str.append(list.get(list.size()-j-1));
				
			}catch(IndexOutOfBoundsException e){e.printStackTrace();}
		}
		return str;
    }
    
    private void equal(){
    
       this.getRPNString();
       
       if (o!=c){
			int difference = o-c;
			for(int i = 0; i < difference; i++){
				this.closingBrackets();
		    }
		}
      
           finalRpnString = this.arrayReverse(operations, finalRpnString);
		   ans = RPN.getAnswer(finalRpnString.toString()); 
       
		   this.clearAll(HelpMethods.getArrayListInStringFormat(inputListener)+ans);
		   gui.getCalcDisplay().setText(ans);
		   this.addLineToTextArea(gui.getConsole(), consoleDisplayLines, "Current value of ANS: "+ans);
		   
	}
    
    private void plus(){
    	
    	if(operations.contains(" *")){
			operations.remove(operations.lastIndexOf(" *"));
			finalRpnString.append(" *");
		}else if(operations.contains(" /")){
			operations.remove(operations.lastIndexOf(" /"));
			finalRpnString.append(" /");
		}
    	
    	this.operation("+", "+", "-");
    }
    
    private void multiply(){
    	this.operation("*", "*", "/");
    }
    
    private void divide(){
    	this.operation("/","*","/");
    }
    
    private void minus(int arrayIndex){
    	
    	if(operations.contains(" *") && !inputListener.get(arrayIndex-1).equals("×")){
			operations.remove(operations.lastIndexOf(" *"));
			finalRpnString.append(" *");
		}else if(operations.contains(" /") && !inputListener.get(arrayIndex-1).equals("÷")){
			operations.remove(operations.lastIndexOf(" /"));
			finalRpnString.append(" /");
		}
	    
     
		if((arrayIndex == 0 && inputListener.get(0).equals("-")) || (inputListener.size() >= 1 && inputListener.get(arrayIndex-1).equals("("))){
			finalRpnString.append("-");
		  
		}else if((inputListener.size() >= 1) && (HelpMethods.thisStringContains(inputListener.get(arrayIndex-1), "÷","×","+","-"))){ 
			
			finalRpnString.append("-");
			
		}else{
			this.operation("-", "-", "+");
		}
    }
    
    private void addLineToTextArea(TextArea field, ArrayList<String> storage , String input){
    	storage.add(0,input);
    	field.clear();
    	if(storage.size()>6) storage.remove(storage.size()-1);
    	storage.forEach((String s) -> field.appendText(s+"\n"));
	}
    
    private void deleteButton(){
    	
    	if(inputListener.isEmpty()) this.clearAll("");
    	else this.clearAll("Delete "+HelpMethods.getArrayListInStringFormat(inputListener));
		
    }
    
    private void addConst(int position){
    	
    	for(int i = 0; i<SaveManager.instance().getValues().length; i++){
    		
    		if(inputListener.get(position).equals(SaveManager.instance().getValues()[i].getConstSign())){
    		finalRpnString.append(SaveManager.instance().getValues()[i].getConstValue());
    		break;
    		}
    	}
    }
    
    private void action(int position){
    	
    	if(!inputListener.isEmpty()){
			
    		switch(inputListener.get(position)){
    		
    		case "sin":finalRpnString.append("mtSIN ");
    		         break;
    		case "cos":finalRpnString.append("mtCOS ");
    		         break;
    		case "tan":finalRpnString.append("mtTAN ");
    		         break;
    		case "tanh":finalRpnString.append("mtTANh ");
	                 break;
    		case "cosh":finalRpnString.append("mtCOSh ");
	                 break;
    		case "sinh":finalRpnString.append("mtSINh ");
	                 break;
    		case "√":finalRpnString.append("SQRT ");
                     break;
    		case "ª√":finalRpnString.append("SQRTX ");
                     break;
            case "ln":finalRpnString.append("LOG "+Math.E+" ");
                     break;
    		case "lg":finalRpnString.append("LOG 10 ");
                     break;
    		case "logX":finalRpnString.append("LOG ");
                     break;
    		case ".":finalRpnString.append(".");
                     break;
    		case "^":finalRpnString.append(" ^ ");
    		         break;
    		case "²":finalRpnString.append(" ^ 2");
    		         break;
    		case "%":finalRpnString.append(" %");
    		         break;
    		case "Rem":finalRpnString.append("rem ");
	                 break;
    		case "π":finalRpnString.append(Math.PI);
	                 break;
    		case "e":finalRpnString.append(Math.E);
	                 break;
    		case "ANS":finalRpnString.append(ans);
	                 break;
    		case "(":this.openingBrackets();
                     break;
    		case ")":this.closingBrackets();
                     break;
    		case ",":finalRpnString.append(' ');
                     break;
    		case "+":this.plus();
                     break;
    		case "-":this.minus(position);
                     break;
    		case "×":this.multiply();
                     break;
    		case "÷":this.divide();
                     break; 
    		default: this.addConst(position);      
    		 }
    	}
    }
    
    private void showHelpMessages(String type){
    	
    	if(type.equals("LOG")){
			this.addLineToTextArea(gui.getConsole(), consoleDisplayLines, "Example of use for logarithm button: \nlogX(3-1),(16+4) = log₂20\nFor radical:\n"
					+ "ª√(5-2),(3×9) = ³√27\nThis message can be disabled in help tab.");
		}
		
		if(type.equals("REM")){
			this.addLineToTextArea(gui.getConsole(), consoleDisplayLines, "This function returns remainder of division.\nExample of use: rem10,3\nwhere 10-dividend 3-divisor. Return: 1."
					+ "\nThis message can be disabled in help tab.");
		}
    	
    }
    
    private void degreesOrRadians(){
    	if(gui.getConverterChoice().getValue().equals("Deg")){
	    	RPN.setUnitOfMeasurement(true);
	    }else if(gui.getConverterChoice().getValue().equals("Rad")){
	    	RPN.setUnitOfMeasurement(false);
	    }
    }
    
    public void handle(ActionEvent event) {
		
		Object src = event.getSource();
		
		Button button = (Button) event.getSource();
		
		this.degreesOrRadians();
		
		if(src == gui.getRoundbtn()) this.roundThis();
		
		if(src == gui.getRevbtn()) this.reverseThis();
		
		if(inputListener.isEmpty()) this.clearAll("");
		
		if(this.addMultiplicationSign(button.getText())) inputListener.add("×");
		
		if(this.addANSIfItFirst(src, gui.getPlusbtn(), gui.getSecDegreebtn(), gui.getDividebtn(), gui.getMultiplybtn())){
			inputListener.add("ANS");
			gui.getCalcDisplay().setText("ANS");
		}
		
		if(src == gui.getDelbtn()) this.deleteButton();
		
		if(src == gui.getUndobtn()) this.undo();
			
		if(src == gui.getRandombtn()) this.random();
		
		if((src == gui.getRootXbtn() || src == gui.getLogXbtn()) && !DataEx.getValue("Hide help messgaes")) this.showHelpMessages("LOG");
		
		if(src == gui.getRemainderbtn() && !DataEx.getValue("Hide help messgaes")) this.showHelpMessages("REM");
		
		if(src != gui.getDelbtn() && src != gui.getUndobtn() 
				&& src != gui.getRandombtn() && src != gui.getRoundbtn() && src != gui.getRevbtn()){
			
			String symbol ="";
			
			switch(button.getText()){
			
			case"x²":symbol = "²";break;
			case"xª":symbol = "^";break;
			default: symbol = button.getText();
			}
			
			inputListener.add(symbol);
			gui.getCalcDisplay().appendText(symbol);
			
		}
		
		if(HelpMethods.thisStringContains(button.getText(), "sin", "cos", "tan","cosh","tanh","sinh","xª","lg","ln","√")){
			inputListener.add("(");
			gui.getCalcDisplay().appendText("(");
		}
		
		if(src == gui.getEqualbtn()) this.equal();
		
	}
    
	private void getRPNString(){
		
		for(int i = 0; i<inputListener.size(); i++){
			
			if((inputListener.get(i).length() == 1) && (HelpMethods.thisStringIsInteger(inputListener.get(i)))){
				
				finalRpnString.append(inputListener.get(i));
				
			}else{
				this.action(i);
			}
		}
	}
	private boolean addANSIfItFirst(Object source,Button... buttons){
		//if textField is empty and i use plus(for instance) button, application add last ANS value automatically
		for(int i = 0;i<buttons.length;i++){
			if((inputListener.isEmpty() && source == buttons[i] && !ans.isEmpty())) return true;
		}
		return false;
	}
	
	private boolean addMultiplicationSign(String buttonText){
		//if i "forgot" assign multiply sign for expressions like 4pi = 4*pi, 2cosx = 2*cosx application do it for me
		if(!(inputListener.isEmpty()) && (HelpMethods.thisStringContains(buttonText, "π", "e", "ANS",
				gui.getConstantButtons()[0].getText(),
				gui.getConstantButtons()[1].getText(),
				gui.getConstantButtons()[2].getText(),
				gui.getConstantButtons()[3].getText(),
				gui.getConstantButtons()[4].getText(),
				gui.getConstantButtons()[5].getText())) 
				&& !(HelpMethods.thisStringContains(inputListener.get(inputListener.size()-1), "÷", "×", "-", "+",",",
        				"sin","sinh","cosh","tanh", "cos", "tan", "ln", "lg", "√","(","logX","ª√","Rem"))){
        	
        	return true;
        	
        }else if(!(inputListener.isEmpty()) && (HelpMethods.thisStringContains(buttonText, "sin","sinh","cosh","tanh", "cos",
        		"(", "tan", "ln", "lg", "√","ª√","logX"))
        		&& !(HelpMethods.thisStringContains(inputListener.get(inputListener.size()-1), "÷", "×", "-", "+","(",",","ª√","logX","Rem"))){
			
			return true;
			
		}
		return false;
	}
	
	public void timerVisibility(){
		if(!DataEx.getValue("Show Timer")) gui.getTimefield().setVisible(false);
		else gui.getTimefield().setVisible(true);
	}
}


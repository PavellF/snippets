package services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import objects.UnitsOfMeasurement;
import util.AppUtilities;

@Component
public class CalculatorServiceImpl implements CalculatorService {

	private StringBuilder finalRpnString; 
	private byte o,c;//o = open, c = close
	private int expressionArraySize = 0 ;
	private List<String> expression; 
    private List<String> rpnStringDump;
    private List<String> operations;
	private String[][] operationsDump;
	private UnitsOfMeasurement um;
	private final String[] ACTIONS =  {"÷","×","+","-"};
	
	public CalculatorServiceImpl(){
		finalRpnString = new StringBuilder(120);
		rpnStringDump = new ArrayList<>();
		operations = new ArrayList<>();
		operationsDump = new String[12][3];
		expression = new ArrayList<>();
	}
   
	@Override
	public String getAnswer(List<String> expression, UnitsOfMeasurement um) {
		clearAll();
		this.expression = expression;
		this.expressionArraySize = expression.size();
		this.um = um;
		getRPNString();
	       
	       if (o!=c){
				int difference = o-c;
				for(int i = 0; i < difference; i++){
					this.closingBrackets();
			    }
			}
	      
	        finalRpnString = this.arrayReverse(operations, finalRpnString);
			String ans = RPN.getAnswer(finalRpnString.toString(),um);
			clearAll();
			return ans;
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
	
    private void openingBrackets(){
    	    
    	    rpnStringDump.add(finalRpnString.toString());                                
			finalRpnString.setLength(0);                                                
			
			o++;
			
			if(!operations.isEmpty()){
				
				operations = AppUtilities.removeSpecificElementFromList(operations, " ") ;
				final int size = operations.size();
				for(byte b = 0; b<size; b++){
					
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
    	
    	    finalRpnString = arrayReverse(operations, finalRpnString);
			
			String buffer = rpnStringDump.get(rpnStringDump.size()-1).concat(RPN.getAnswer(finalRpnString.toString(),um));
    	    
    	    finalRpnString.setLength(0);
    	    
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
    
    private StringBuilder arrayReverse(List<String> list, StringBuilder str){
    	final int size = list.size();
		for(int j = 0; j<size; j++){
			try{
				
				str.append(list.get(list.size()-j-1));
				
			}catch(IndexOutOfBoundsException e){e.printStackTrace();}
		}
		return str;
    }
    
    private void clearAll(){
    	finalRpnString.setLength(0);
    	o = 0;
    	c = 0;
    	expressionArraySize = 0;
    	expression.clear();
    	rpnStringDump.clear();
    	operations.clear();
    	operationsDump = AppUtilities.fillThisArray(operationsDump, "");
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
    	final String elementBefore = expression.get(arrayIndex-1);
    	
    	if(operations.contains(" *") && !elementBefore.equals("×")){
			operations.remove(operations.lastIndexOf(" *"));
			finalRpnString.append(" *");
		}else if(operations.contains(" /") && !elementBefore.equals("÷")){
			operations.remove(operations.lastIndexOf(" /"));
			finalRpnString.append(" /");
		}
	    
     
		if((arrayIndex == 0 && expression.get(0).equals("-")) || (expressionArraySize >= 1 && elementBefore.equals("("))){
			finalRpnString.append("-");
		  
		}else if((expressionArraySize >= 1) && (AppUtilities.isThisArrayHasSpecificElement(elementBefore,ACTIONS))){ 
			
			finalRpnString.append("-");
			
		}else{
			this.operation("-", "-", "+");
		}
    }
    
    private void action(String value,int position){
    	
    	switch(value){
    		
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
    	case "(":this.openingBrackets();
        break;
    	case ")":this.closingBrackets();
        break;
    	case "‚":finalRpnString.append(" ");
        break;
    	case "+":this.plus();
        break;
    	case "-":this.minus(position);
        break;
    	case "×":this.multiply();
        break;
    	case "÷":this.divide();
        break; 
    	case "!":finalRpnString.append(" ! ");
    	break;
    	}
    	
    }
    
    private StringBuilder getRPNString(){
    	for(int i = 0;i<expressionArraySize;i++){
    		String val = expression.get(i);
    		if(AppUtilities.thisStringIsNumber(val)){
				finalRpnString.append(val);
			}else{
				action(val,i);
			}
    	}
    	return finalRpnString;
	}
    
}





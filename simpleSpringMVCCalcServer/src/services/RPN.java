package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import exceptions.IncorrectExpressionException;
import objects.UnitsOfMeasurement;
import util.MathUtilities;

public class RPN {
	
	public static String getAnswer(String exp,UnitsOfMeasurement um){
		try{
		List<String> expArray = new ArrayList<String>(Arrays.asList(exp.split(" ")));
		
		for(int j = 0; j < expArray.size(); j++){
			 String jElement =  expArray.get(j);
			 expArray.set(j, jElement.replace("--", ""));
			 
			 if(jElement.startsWith("mt") || jElement.startsWith("-mt")){
				 trigonometryFunc(expArray,j,um);
				 j--;
			}else if(jElement.equals("%")){
				percent(expArray,j);
				j--;
			}else if((jElement.equals("LOG")) || (jElement.equals("SQRTX")) || (jElement.equals("rem"))){
				 advancedFunc(expArray,j);
				 j--;
			}else if(jElement.equals("^")){
				degree(expArray,j);
				j--;
				j--;
			}else if(jElement.equals("SQRT")){
				squareRoot(expArray,j);
				j--;
			}else if(jElement.equals("!")){
				factorial(expArray,j);
				j--;
			}else if(jElement.equals("*") || jElement.equals("-") || jElement.equals("+") || jElement.equals("/")){
				action(expArray,j);
				j--;
				j--;
			}
		 }
		return expArray.get(0);
		}catch(Exception e){
			e.printStackTrace();
			throw new IncorrectExpressionException("Expression is empty/too short or has wrong syntax.");
		}
	 }
	
	private static void trigonometryFunc(List<String> expArray,int currentElementPosition,UnitsOfMeasurement um){
	
		String currentElement = expArray.get(currentElementPosition);
		double nextNumber = Double.parseDouble(expArray.get(currentElementPosition+1));
		double result = 0.00;
		if(um == UnitsOfMeasurement.DEGREES) nextNumber = Math.toRadians(nextNumber);
		
		switch(currentElement.replace("-", "")){
		case "mtSIN": result = Math.sin(nextNumber);
		break;
		case "mtCOS": result = Math.cos(nextNumber);
		break;
		case "mtTAN": result = Math.tan(nextNumber);
		break;
		case "mtTANh": result = Math.tanh(nextNumber);
		break;
		case "mtSINh": result = Math.sinh(nextNumber);
		break;
		case "mtCOSh": result = Math.cosh(nextNumber);
		break;
		}
		
		if(currentElement.startsWith("-")) expArray.set(currentElementPosition, "-"+result);
		else expArray.set(currentElementPosition, ""+result);
		
		expArray.remove(currentElementPosition+1);
	
	}
	
	private static void percent(List<String> expArray,int currentElementPosition){
		String nextElement = expArray.get(currentElementPosition+1);
		double numberOne = Double.parseDouble(expArray.get(currentElementPosition-1));
		double numberTwo = Double.parseDouble(expArray.get(currentElementPosition-2));
		
		if(nextElement.equals("*") || nextElement.equals("/")){
	    	 expArray.set(currentElementPosition-1, (numberOne/100)+""); 
	     }else if(nextElement.equals("+") || nextElement.equals("-")){
	    	expArray.set(currentElementPosition-1, ((numberOne*numberTwo)/100)+"");
	    }
	
	    expArray.remove(currentElementPosition);
	}
	
	private static void advancedFunc(List<String> expArray,int currentElementPosition){
		
			double result = 00.00;
			double numberOne = Double.parseDouble(expArray.get(currentElementPosition+1));
			double numberTwo = Double.parseDouble(expArray.get(currentElementPosition+2));
			String currentElement = expArray.get(currentElementPosition);
			
			if(currentElement.equals("rem")) result = Math.IEEEremainder(numberOne, numberTwo);
		 
			if(currentElement.equals("SQRTX")) result = Math.pow(numberTwo, (1/numberOne));
		
	    	if((currentElement.equals("LOG"))) result = MathUtilities.getLogarithmWithAnyBase(numberOne, numberTwo);
	    	
	   
	    	if(currentElement.startsWith("-")) expArray.set(currentElementPosition, "-"+result);
	    	else expArray.set(currentElementPosition, ""+result);
	    
	    	expArray.remove(currentElementPosition+1);
	    	expArray.remove(currentElementPosition+1);
	}
	
	private static void degree(List<String> expArray,int currentElementPosition){
		double numberOne = Double.parseDouble(expArray.get(currentElementPosition-1));
		double numbertwo = Double.parseDouble(expArray.get(currentElementPosition+1));
		
		expArray.set(currentElementPosition, Math.pow(numberOne, numbertwo)+"");
		expArray.remove(currentElementPosition-1);
		expArray.remove(currentElementPosition);
	}
	
	private static void squareRoot(List<String> expArray,int currentElementPosition){
		double numberOne = Double.parseDouble(expArray.get(currentElementPosition+1));
		String currentElement = expArray.get(currentElementPosition);
		
		if(currentElement.startsWith("-")) expArray.set(currentElementPosition, "-"+Math.sqrt(numberOne));
		else expArray.set(currentElementPosition, Math.sqrt(numberOne)+"");
		expArray.remove(currentElementPosition+1);
	}
	
	private static void action(List<String> expArray,int currentElementPosition){
		String currentElement = expArray.get(currentElementPosition);
		double numberOne = Double.parseDouble(expArray.get(currentElementPosition-2));
		double numberTwo = Double.parseDouble(expArray.get(currentElementPosition-1));
		
		switch(currentElement){
		case "*":
			expArray.set(currentElementPosition, Double.toString(numberOne*numberTwo));
			break;
		case "-":	
			expArray.set(currentElementPosition, Double.toString(numberOne-numberTwo));
			break;
		case "+":
			expArray.set(currentElementPosition, Double.toString(numberOne+numberTwo));
			break;
		case "/":
			expArray.set(currentElementPosition, Double.toString(numberOne/numberTwo));
			break;
		}
		
		expArray.remove(currentElementPosition-1);
		expArray.remove(currentElementPosition-2);
	}
	
	private static void factorial(List<String> expArray,int currentElementPosition){
		String answer = "";
		String input = expArray.get(currentElementPosition-1);
		
		if(input.matches("[0-9]+")){
			long numberOne = Long.parseLong(input);
			
			if(numberOne < 0){
				answer = "-";
				numberOne = Math.abs(numberOne);
			}
			answer = answer.concat(MathUtilities.factorial(numberOne)+"");
		}else{
			double numberOne = Double.parseDouble(input);
			
			if(numberOne < 0){
				answer = "-";
				numberOne = Math.abs(numberOne);
			}
			answer = answer.concat(MathUtilities.gamma(numberOne+1)+"");
		}
		
		expArray.set(currentElementPosition, answer);
		expArray.remove(currentElementPosition-1);
	}
	
}



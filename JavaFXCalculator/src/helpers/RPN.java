package helpers;

import java.util.ArrayList;
import java.util.Arrays;

public class RPN {
	
	private static boolean degrees = false;

	public static void setUnitOfMeasurement(boolean value){
		degrees = value;
	}
	
	public static String getAnswer(String exp){
		
		String answer = new String();
		Double resultDouble = null;
		ArrayList<String> expArray = new ArrayList<String>(Arrays.asList(exp.split(" ")));
	
	 for(int j = 0; j < expArray.size(); j++){
		 
		 expArray.set(j, expArray.get(j).replace("--", ""));
		 
		 if(expArray.get(j).startsWith("mt") || expArray.get(j).startsWith("-mt")){
			
			 try{
				
				Double doubleOne = Double.parseDouble(expArray.get(j+1));
				
				if(degrees == true) doubleOne = Math.toRadians(doubleOne);
				
				switch(expArray.get(j).replace("-", "")){
				case "mtSIN": resultDouble = Math.sin(doubleOne);
				break;
				case "mtCOS": resultDouble = Math.cos(doubleOne);
				break;
				case "mtTAN": resultDouble = Math.tan(doubleOne);
				break;
				case "mtTANh": resultDouble = Math.tanh(doubleOne);
				break;
				case "mtSINh": resultDouble = Math.sinh(doubleOne);
				break;
				case "mtCOSh": resultDouble = Math.cosh(doubleOne);
				break;
				}
				
				if(expArray.get(j).startsWith("-")) expArray.set(j, "-"+resultDouble);
				
				                               else expArray.set(j, ""+resultDouble);
				
				expArray.remove(j+1);
				
				j--;
			}catch(Exception e){e.printStackTrace(); return exp;}
		
		 }else if(expArray.get(j).equals("%")){
		
			if(expArray.get(j+1).equals("*") || expArray.get(j+1).equals("/")){
		    	 expArray.set(j-1, Double.parseDouble(expArray.get(j-1))/100+""); 
		     }else if(expArray.get(j+1).equals("+") || expArray.get(j+1).equals("-")){
		    	expArray.set(j-1, (Double.parseDouble(expArray.get(j-1))*Double.parseDouble(expArray.get(j-2)))/100+"");
		    }
		
		        expArray.remove(j);
				j--;
				
		 }else if((expArray.get(j).equals("LOG")) || (expArray.get(j).equals("SQRTX")) || (expArray.get(j).equals("rem"))){
			 try{
			   
				if(expArray.get(j).equals("rem")) resultDouble = Math.IEEEremainder(Double.parseDouble(expArray.get(j+1)), Double.parseDouble(expArray.get(j+2)));
				 
				if(expArray.get(j).equals("SQRTX")) resultDouble = Math.pow(Double.parseDouble(expArray.get(j+2)), 1/Double.parseDouble(expArray.get(j+1)));
				
			    if((expArray.get(j).equals("LOG"))) resultDouble = Math.log(Double.parseDouble(expArray.get(j+2)))
			    		/ Math.log(Double.parseDouble(expArray.get(j+1)));
			    	
			   
			    if(expArray.get(j).startsWith("-")) expArray.set(j, "-"+resultDouble);
				
                else expArray.set(j, ""+resultDouble);
			    
			    expArray.remove(j+1);
			    expArray.remove(j+1);
			   
			    j--;
				}catch(Exception e){e.printStackTrace(); return exp;}
		 }else if((expArray.get(j).equals("^")) || (expArray.get(j).equals("SQRT"))){
			try{
				
				
				if(expArray.get(j).equals("^")){
				
				    expArray.set(j, Math.pow(Double.parseDouble(expArray.get(j-1)), Double.parseDouble(expArray.get(j+1)))+"");
					expArray.remove(j-1);
					j--;
				}else{
				
					resultDouble = Math.sqrt(Double.parseDouble(expArray.get(j+1)));
				
					if(expArray.get(j).startsWith("-")) expArray.set(j, "-"+resultDouble);
					
	                else expArray.set(j, resultDouble+"");
				}
				
				expArray.remove(j+1);
				
				j--;
			}catch(Exception e){e.printStackTrace(); return exp;}
		
		}else if(expArray.get(j).equals("*") || expArray.get(j).equals("-") || expArray.get(j).equals("+") || expArray.get(j).equals("/")){
			try{
			
			switch(expArray.get(j)){
			case "*":
				expArray.set(j, Double.toString(Double.parseDouble(expArray.get(j-2))*Double.parseDouble(expArray.get(j-1))));
				break;
			case "-":	
				expArray.set(j, Double.toString(Double.parseDouble(expArray.get(j-2))-Double.parseDouble(expArray.get(j-1))));
				break;
			case "+":
				expArray.set(j, Double.toString(Double.parseDouble(expArray.get(j-2))+Double.parseDouble(expArray.get(j-1))));
				break;
			case "/":
				expArray.set(j, Double.toString(Double.parseDouble(expArray.get(j-2))/Double.parseDouble(expArray.get(j-1))));
				break;
			}
			
			expArray.remove(j-1);
			expArray.remove(j-2);
			
			j--;
			j--;
			}catch(Exception e){return exp;}
		}
	 }
	
	 String[] answerSplitArray =  expArray.get(0).split("");
	 
	 for(int i = 0; i<answerSplitArray.length; i++){
		 
		 int m = answerSplitArray.length-i;
		 
		 if(answerSplitArray[i].equals(".") && m==2 && answerSplitArray[answerSplitArray.length-1].equals("0")){
			 answerSplitArray[i] = "";
			 answerSplitArray[answerSplitArray.length-1] = "";
		}
	}
	 
	 for(int i = 0; i<answerSplitArray.length; i++){
			answer = answer+answerSplitArray[i];
	 }
	 
	 return answer;
	 }
	}


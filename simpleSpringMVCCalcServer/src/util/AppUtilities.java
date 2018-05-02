package util;

import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class AppUtilities {

	public static boolean thisStringIsNumber( String string){
		try{
			Double.parseDouble(string);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	public static <T> boolean isThisArrayHasSpecificElement(T element, T[] array){
	    	final int length = array.length;
	    	for(int i = 0; i < length; i++){
	    		
	    		if(element.equals(array[i])) return true;
	    	}
	    	
	    	return false;
	}
	
	 public static <T> T[][] fillThisArray(T[][] array, T smth){
	    	
	    	for(int r = 0; r<array.length; r++){
	    		
	    		for(int c = 0; c<array[r].length; c++){
	    			array[r][c] = smth;
	    		}
	    	}
	    	
	    	return array;
	}
	
	 public static <T> List<T> removeSpecificElementFromList(List<T> list, T element){
	    	
	        for(int i = 0; i<list.size(); i++){
	    	  if(list.get(i).equals(element)){
				list.remove(i);
				i--;
			}
	       }
	    	return list;
	}
	
	public static String stringListToString(List<String> list,String delimeter){
		return String.join(delimeter, list);
	}
	
	
}


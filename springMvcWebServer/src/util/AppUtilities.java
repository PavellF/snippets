package util;

import java.util.List;

public class AppUtilities {
	
	private static final String[] ALPHABET = {"a","b","c","d","e","f","g","h","i","j","k","l","m",
	                                                   "n","o","p","q","r","s","t","u","v","w","x","y","z",
	                                                   "A","B","C","D","E","F","G","H","I","J","K","L","M",
	                                                   "N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
	                                                   "0","1","2","3","4","5","6","7","8","9"};
	private static final int ALPHABET_LENGTH = ALPHABET.length;
	
	public static String generateRandomStringValue(int length){
		StringBuilder word = new StringBuilder(length);
		
		for(int a = 0; a<length; a++){
			word.append(ALPHABET[(int) Math.floor(ALPHABET_LENGTH*Math.random())]);
		}
		return word.toString();
	}

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
	
	public static boolean isStringContainsOnlyWhitespaces(String incoming){
		
		char[] array = incoming.toCharArray();
		
		for(int i = 0; i<array.length; i++){
			if(array[i] != ' ') return false;
		}
		
		return true;
	}

}


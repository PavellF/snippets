package helpers;
import java.util.ArrayList;

public class HelpMethods {

	public static String[][] arrayPlaceholder(String[][] array, String smth){
    	
    	for(int r = 0; r<array.length; r++){
    		
    		for(int c = 0; c<array[r].length; c++){
    			array[r][c] = smth;
    		}
    	}
    	
    	return array;
    }
    
    public static String rebuildString(String sourse, String splitRegex, int delete, boolean addSpace){
    	
    	String[] parseSrc = sourse.split(splitRegex);
    	
    	while(delete > 0){
    		
    		parseSrc[parseSrc.length-delete] = "";
    		delete--;
    	}
    	
    	sourse = "";
    	
    	for(int i = 0; i<parseSrc.length; i++){
    		if((addSpace == true) && !(i == 0)){
    			sourse = sourse+" "+parseSrc[i];
    		}else{
    		   sourse = sourse+parseSrc[i];
    		}
    	}
    	
    	return sourse;
    }
    
    public static String getArrayListInStringFormat(ArrayList<String> list){
    	
    	StringBuffer input = new StringBuffer(256);
    	
    	for(int i = 0; i<list.size(); i++){
           input.append(list.get(i));
    	}
    	return input.toString();
    }
    
    public static boolean thisStringIsInteger(String stringWithNumber){
	  try{
		  Integer.parseInt(stringWithNumber);
		  return true;
	  }catch(NumberFormatException e){
		  return false;
	  }
	}
 
    public static boolean thisStringIsDouble(String stringWithNumber){
      try{
  		  Double.parseDouble(stringWithNumber);
  		  return true;
  	  }catch(NumberFormatException e){
  		  return false;
  	  }
  	}
    	
    public static boolean thisStringContains(String str, String... strings){
    	
    	for(int i = 0; i < strings.length; i++){
    		
    		if(str.equals(strings[i])) return true;
    	}
    	
    	return false;
    }
    
   
    public static ArrayList<String> removeSpecificElementFromArrList(ArrayList<String> list, String element){
    	
        for(int i = 0; i<list.size(); i++){
    	  if(list.get(i).equals(element)){
			list.remove(i);
		}
       }
    	return list;
    }
    
    public static int getRandomInt(int start, int end){
    	
    	if(start > end){
    		throw new IllegalArgumentException("Start int greater than end int.");
    	}
    	
    	long difference =  end-start+1;
    	long randomNumber = (long) (difference*Math.random()); 
    	int result = start + (int) randomNumber;
    	
    	return result;
    }
}

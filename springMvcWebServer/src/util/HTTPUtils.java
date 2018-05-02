package util;

import enums.Order;
import enums.OriginType;
import enums.SortBy;
import exceptions.InvalidRequestParameterException;
import internationalization.Locale;

public class HTTPUtils {


	public static int parseInteger(int min,int max,String incoming){
		try{
			
			int result = Integer.parseInt(incoming);
			
			if(result>max || result<min){
				throw new  InvalidRequestParameterException("One of request params is integer but values greater than "
			+max+" or less than "+min+" are not allowed.");
			}
			
			return result;
		}catch(NumberFormatException e){
			throw new  InvalidRequestParameterException("One of request params is not Integer value. Change its value to integer.");
		}
	}
	
	public static long parseLong(String incoming){
		try{
			
			long result = Long.parseLong(incoming);
			
			return result;
		}catch(NumberFormatException e){
			throw new  InvalidRequestParameterException("Fail. Value is not number.");
		}
	}
	
	public static boolean parseBoolean(String incoming){
		
		if(incoming.equals("true")){
			
			return true;
			
		}else if(incoming.equals("false")){
			
			return false;
			
		}else{
			throw new InvalidRequestParameterException("One of request params is not boolean value. Change to true or false.");
		}
	}
	
	public static Enum<?> parseEnum(String incoming, Enum<?>... allowed){
		
		for(int i = 0; i<allowed.length; i++){
			
			if(allowed[i].toString().equals(incoming)){
				return allowed[i];
			}
		}
		
		return null;
	}
	
	public static void main(String...strings){
		
		System.out.println(Order.ASCENDING.toString().equals("ASCENDING"));
		
		System.out.println(parseEnum("DATE",SortBy.FRIENDSHIP_DATE,SortBy.NAME,SortBy.RATING));
		
		OriginType originType = (OriginType) 
				HTTPUtils.parseEnum("ABC", OriginType.POSTED_CONTENT,OriginType.USER_PROFILE);
		
		System.out.println(originType);
	}
	/**never throws exception*/
	public static Long parseID(String in){
		
		long out;
		
		try{
			out = Long.parseLong(in);
		}catch(NumberFormatException nfe){
			return null;
		}
		
		return out;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

package internationalization;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class InternationalizationFileImp implements InternationalizationFile {

	private String pathToFile;
	private Map<String, String> entryNameValuePairs;
	private static final Logger logger = LogManager.getLogger(InternationalizationFileImp.class.getName());
	
	public InternationalizationFileImp(Map<String, String> entryNameValuePairs, String pathToFile){
		this.pathToFile = pathToFile;
		this.entryNameValuePairs = entryNameValuePairs;
	}
	
	@Override
	public String getString(String entryName) {
		
		String value;
		
		try{
		
			value = entryNameValuePairs.get(entryName);
			
		}catch(ClassCastException | NullPointerException e){
			
			logger.error("Value associated with this key does not exist. Null is returning.");
			
			return null;
			
		}
		
		logger.info("Success. Returning value as a string.");
		
		return value;
	}

	@Override
	public int getInt(String entryName) {
		
		int value;
		
		try{
			
			logger.info("Try to parse value as a integer..");
			
			value = Integer.parseInt(entryNameValuePairs.get(entryName));
			
		}catch(NumberFormatException e){
			
			throw new ParseException();
			
		}catch(ClassCastException | NullPointerException e){
			
			logger.error("Value associated with this key does not exist. Zero is returning.");
			
			return 0;
			
		}
		
		logger.info("Success. Returning value as a int.");
		
		return value;
	}

	@Override
	public long getLong(String entryName) {
		
		long value;
		
		try{
			
			logger.info("Try to parse value as a long..");
			
			value = Long.parseLong(entryNameValuePairs.get(entryName));
			
		}catch(NumberFormatException e){
			
			throw new ParseException();
			
		}catch(ClassCastException | NullPointerException e){
			
			logger.error("Value associated with this key does not exist. Zero is returning.");
			
			return 0;
			
		}
		
		logger.info("Success. Returning value as a long.");
		
		return value;
	}

	@Override
	public double getDouble(String entryName) {

		double value;
		
		try{
			
			logger.info("Try to parse value as a double..");
			
			value = Double.parseDouble(entryNameValuePairs.get(entryName));
			
		}catch(NumberFormatException e){
			
			throw new ParseException();
			
		}catch(ClassCastException | NullPointerException e){
			
			logger.error("Value associated with this key does not exist. Zero is returning.");
			
			return 0;
			
		}
		
		logger.info("Success. Returning value as a double.");
		
		return value;
	}

	@Override
	public boolean getBoolean(String entryName) {
		
		boolean value;
		
		try{
			
			logger.info("Try to parse value as a boolean..");
			
			value = Boolean.parseBoolean(entryNameValuePairs.get(entryName));
			
		}catch(NumberFormatException e){
			
			throw new ParseException();
			
		}catch(ClassCastException | NullPointerException e){
			
			logger.error("Value associated with this key does not exist. False is returning.");
			
			return false;
			
		}
		
		logger.info("Success. Returning value as a boolean.");
		
		return value;
	}

	@Override
	public String getFilesystemPath() {
		return pathToFile;
	}

	@Override
	public Map<String, String> getFile() {
		return entryNameValuePairs;
	}

}

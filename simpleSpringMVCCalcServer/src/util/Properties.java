package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Properties {

	private static Properties properties;
	private Map<String,String> kvPairs;
	private static final Logger logger = LogManager.getLogger(Properties.class.getName());

	private Properties(String... path){
		this.kvPairs = load(setSource(path));
	}
	
	public static Properties get(){
		if(properties == null){
			throw new NullPointerException("This class should be instantiated before call this method.");
		}
		return properties;
	}
	
	public static Properties instance(String... path){
		if(properties == null){
			properties = new Properties(path);
		}else{
			logger.error("This class is already instantiated. To get already existing instance use get() method.");
		}
		return properties;
	}
	
	private Path[] setSource(String... path) {
		
		final int length = path.length;
		Path[] pathArray = new Path[length];
		
		for(int i =0;i<length;i++){
	
			pathArray[i] = Paths.get(path[i]);
		
			if(Files.exists(pathArray[i])){
				logger.info("The file exists. System default charset encoding is: "+Charset.defaultCharset());
			}else{
				 throw new NullPointerException("File does not exist.");
			}
		}
		return pathArray;
	}
	
	private Map<String,String> load(Path...paths){
		
		Map<String, String> entryNameValuePairs = new HashMap<String, String>();
		
		for(int i = 0;i<paths.length;i++){
			try{
				BufferedReader bufferedReader = Files.newBufferedReader(paths[i], StandardCharsets.UTF_8);
			
				String currentLine;
			
				while((currentLine = bufferedReader.readLine()) != null){

					String[] entry = currentLine.split("=",2);
					entryNameValuePairs.put(entry[0], entry[1]);

				}
			
			}catch(IOException ioe){
				logger.error(ioe.getMessage());
				return null;
			}
		}
		return entryNameValuePairs;
	}
	
	public String getString(String entryName) {
		String val = kvPairs.get(entryName);
		logger.info("Retrieved value: "+val);
		return val;
	}

	public int getInt(String entryName) {
		int val = Integer.parseInt(kvPairs.get(entryName));
		logger.info("Retrieved value: "+val);
		return val;
	}

	public long getLong(String entryName) {
		long val =  Long.parseLong(kvPairs.get(entryName));
		logger.info("Retrieved value: "+val);
		return val;
	}

	public double getDouble(String entryName) {
		double val = Double.parseDouble(kvPairs.get(entryName));
		logger.info("Retrieved value: "+val);
		return val;
	}

	public boolean getBoolean(String entryName) {
		boolean val = Boolean.parseBoolean(kvPairs.get(entryName));
		logger.info("Retrieved value: "+val);
		return val;
	}
}

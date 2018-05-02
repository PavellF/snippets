package services;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class SerializableLoader <T>{

	static final Logger logger = LogManager.getLogger(SerializableLoader.class.getName());
	private String pathToSavedFile;
	
	public SerializableLoader(String pathToSavedFile){
		this.pathToSavedFile = pathToSavedFile;
	}
	
	@SuppressWarnings("unchecked")
	public T loadFile(){

		T serializable = null;
		
		try(ObjectInputStream ois = new ObjectInputStream(
				Files.newInputStream(Paths.get(pathToSavedFile)))){
			
			serializable = (T) ois.readObject();
			
		}catch(IOException  ioe){
			logger.error(ioe.getMessage());
		}catch(ClassNotFoundException cnfe){
			logger.error(cnfe.getMessage()); 
		}
		return serializable;
	}
	
	public boolean saveFile(T file){
		try(ObjectOutputStream ous = new ObjectOutputStream(Files.newOutputStream(Paths.get(pathToSavedFile),
				StandardOpenOption.CREATE))){
			
			ous.writeObject(file);
			return true;
		}catch(IOException ioe){
			logger.error(ioe.getMessage());
			return false;
		}
	}
	
}

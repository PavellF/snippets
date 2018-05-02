package helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import serializable.CurrentConstant;
import serializable.Preferences;

public class SaveManager{

	private  CurrentConstant[] values = new CurrentConstant[6];
	private  Preferences prefs;
	private  String description = ""; 
	private static SaveManager saveManager;
	private static final String USER_DIR = System.getProperty("user.dir");
	
	private SaveManager(){}
	
	public static SaveManager instance(){
		if(saveManager == null)
			saveManager = new SaveManager();
		return saveManager;
	}
	
	private CurrentConstant[] loadConstValues(){
		
		CurrentConstant[] cs = null;
		
		try(ObjectInputStream ois = new ObjectInputStream(
				Files.newInputStream(Paths.get(USER_DIR+"/myValues.val")))){
			
			cs = (CurrentConstant[]) ois.readObject();
			
		}catch(IOException ioe){
			System.err.println("ERROR: "+ioe.getMessage()); 
		}catch(ClassNotFoundException cnfe){
			System.err.println("ERROR: "+cnfe.getMessage()); 
		}
		return cs;
	}
	
	public void crateORloadConstValues(){
		
        if(Files.exists(Paths.get(USER_DIR+"/myValues.val"))){
			
        	values = loadConstValues();
        	
		}else{
			//if it first run
			for(int i = 0; i<values.length;i++){values[i] = new CurrentConstant("MyC", "0");}
			
			saveFile(values,System.getProperty("user.dir")+"/myValues.val");
		}
	}
   
    public void saveFile(Object pref,String path){
		
		try(ObjectOutputStream ous = new ObjectOutputStream(Files.newOutputStream(Paths.get(path),
				StandardOpenOption.CREATE))){
			
			ous.writeObject(pref);
			
		}catch(IOException ioe){System.err.println("ERROR: "+ioe.getMessage());}
	}
	
	private Preferences loadCurrentPrefs(){
		
		Preferences p = null;
		
		try(ObjectInputStream oos = new ObjectInputStream(Files.newInputStream(Paths.get(USER_DIR+"/preferences.pre")))){
			
			p = (Preferences) oos.readObject();
			
		}catch(IOException ioe){
			ioe.getMessage();
		}catch(ClassNotFoundException cnfe){
			cnfe.getMessage();
		}
		return p;
	}
	
	public void createOrLoadPrefs(){

	if(Files.exists(Paths.get(USER_DIR+"/preferences.pre"))){
			
       	prefs = loadCurrentPrefs();
       	
		}else{
			//if it first run
			prefs = new Preferences(0, 0, 0, false, false,"White",0,0,0);
			
			saveFile(prefs,System.getProperty("user.dir")+"/preferences.pre");
		}
	}
	
	public String loadDescription(){
		
		try(InputStream in = SaveManager.class.getResourceAsStream("desc/desc.de"); 
				BufferedReader br = new BufferedReader(new InputStreamReader(in));){
			
			String currLine;
			
			while((currLine = br.readLine()) != null){
				 description += currLine+"\n";
			}
			
		}catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }catch(IOException ioe){
			ioe.printStackTrace();
		}
		return description;
	}
	
	public CurrentConstant[] getValues(){
		return values;
	}
	public Preferences getPreferences(){
		return prefs;
	}
}

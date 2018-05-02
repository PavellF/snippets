package internationalization;

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


public class InternationalizationServiceImp implements InternationalizationService{

	private static final Logger logger = LogManager.getLogger(InternationalizationServiceImp.class.getName());
	private Map<String, InternationalizationFile> aliasLocalizationFilePairs;
	private Charset encoding;
	private Locale defaultLocale;
	
	public InternationalizationServiceImp(Map<String, String> pathAliasPairs, Locale defaultLocale){
		this(pathAliasPairs, StandardCharsets.UTF_8, defaultLocale);
	}
	
	public InternationalizationServiceImp(Map<String, String> pathAliasPairs, Charset encoding, Locale defaultLocale){
		
		this.aliasLocalizationFilePairs = new HashMap<>();
		this.encoding = encoding;
		this.defaultLocale = defaultLocale;
		
		pathAliasPairs.forEach((key,value) -> {
			
			aliasLocalizationFilePairs.put(value,load(key));
			logger.info("Localization file "+value+" is loaded.");
		});
		
		logger.info("LocalizationServiceImp is instantiated.");
	}
	
	private InternationalizationFile load(String path){
		
		Path pathToFile = Paths.get(path);
		
		try{
			if(Files.exists(pathToFile)){
				StringBuffer info = new StringBuffer();
				info.append("The file exists. ");
				info.append("The absolute path is: ");
				info.append(pathToFile.toAbsolutePath().toString());
				info.append(" The file size is: ");
				info.append(Files.size(pathToFile));
				info.append(" System default charset encoding is: ");
				info.append(Charset.defaultCharset());
				logger.info(info.toString());
			}else{
				throw new FileDoesNotExistException();
			}
			
			BufferedReader bufferedReader = Files.newBufferedReader(pathToFile, encoding);
			
			Map<String, String> entryNameValuePairs = new HashMap<String, String>();
			
			String currentLine;
			
			while((currentLine = bufferedReader.readLine()) != null){

		        String[] entry = currentLine.split("=",2);
		        entryNameValuePairs.put(entry[0], entry[1]);

		      }
			
			InternationalizationFile localizationFile = new InternationalizationFileImp(
					entryNameValuePairs,
					pathToFile.toAbsolutePath().toString()
					);
			
			return localizationFile;
			
		}catch(IOException ioe){
			logger.error("Some problems with file. "+ioe.getMessage());
		}
		
		return null;
	}

	@Override
	public Charset getCharset() {
		return encoding;
	}

	private boolean isThisInternationalizationFileExists(String alias) {
		return aliasLocalizationFilePairs.containsKey(alias);
	}

	@Override
	public Map<String, String> getAllFileContent(String fileAlias, Locale country) {
		
		InternationalizationFile localizationFile;
		String fullFileAlias = fileAlias.concat(country.toString());
		
		if(isThisInternationalizationFileExists(fullFileAlias)){
			
			localizationFile = aliasLocalizationFilePairs.get(fullFileAlias);
			logger.info("File with "+fullFileAlias+" alias exists.");
			
		}else{
			
			logger.info("File with "+fullFileAlias+" alias does not exist. Trying to get a default country localization file.");
			fullFileAlias = fileAlias.concat(this.defaultLocale.toString());
			
			localizationFile = aliasLocalizationFilePairs.get(fullFileAlias); 
			
			if(localizationFile == null){
				throw new FileDoesNotExistException();
			}
			logger.info("File with "+fullFileAlias+" alias exists.");
		}
		
		return localizationFile.getFile();
		
	}

	@Override
	public Object getFileEntry(String fileAlias, Locale country, String key, Class<?> expected) {
		
		InternationalizationFile localizationFile;
		String fullFileAlias = fileAlias.concat(country.toString());
		
		if(isThisInternationalizationFileExists(fullFileAlias)){
			
			localizationFile = aliasLocalizationFilePairs.get(fullFileAlias);
			logger.info("File with "+fullFileAlias+" alias exists.");
			
			return retrieveKey(localizationFile,key,expected);
			
		}else{
			
			logger.info("File with "+fullFileAlias+" alias does not exist. Trying to get a default country localization file.");
			fullFileAlias = fileAlias.concat(this.defaultLocale.toString());
			
			if(isThisInternationalizationFileExists(fullFileAlias)){
				
				localizationFile = aliasLocalizationFilePairs.get(fullFileAlias);
				logger.info("File with "+fullFileAlias+" alias exists.");
				return retrieveKey(localizationFile,key,expected);
				
			}else {
				throw new FileDoesNotExistException();
			}
		}
	}
	
	private Object retrieveKey(InternationalizationFile localizationFile, String key, Class<?> expected){
		
		if(localizationFile.getFile().containsKey(key)){
			
			if(expected.getName().equals(String.class.getName())){
				return localizationFile.getString(key);
			}else if(expected.getName().equals(int.class.getName())){
				return localizationFile.getInt(key);
			}else if(expected.getName().equals(double.class.getName())){
				return localizationFile.getDouble(key);
			}else if(expected.getName().equals(long.class.getName())){
				return localizationFile.getLong(key);
			}else if(expected.getName().equals(boolean.class.getName())){
				return localizationFile.getBoolean(key);
			}else{
				throw new IllegalArgumentException("Given class is not valid.");
			}
			
		}else{
			throw new NullPointerException("Given key does not exist.");
		}
		
	}

	@Override
	public Locale getDefaultLocale() {
		return this.defaultLocale;
	}

	@Override
	public Map<String, String> mergeAndGetAllFileContent(Locale country, String... aliases) {
		
		Map<String, String> merged = new HashMap<>();
		
		for(int i = 0; i<aliases.length; i++){
			merged.putAll(this.getAllFileContent(aliases[i], country));
		}
		
		return merged;
	}

	
}

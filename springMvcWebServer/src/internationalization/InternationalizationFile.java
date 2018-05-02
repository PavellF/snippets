package internationalization;

import java.util.Map;

public interface InternationalizationFile {

	public String getString(String entryName);
	
	public Map<String,String> getFile();
	
	public int getInt(String entryName);
	
	public long getLong(String entryName);
	
	public double getDouble(String entryName);
	
	public boolean getBoolean(String entryName);
	
	public String getFilesystemPath();
	
}

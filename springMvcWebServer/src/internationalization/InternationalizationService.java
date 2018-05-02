package internationalization;

import java.nio.charset.Charset;
import java.util.Map;


public interface InternationalizationService {

	public Map<String, String> getAllFileContent(String fileAlias, Locale country);
	
	public Map<String, String> mergeAndGetAllFileContent(Locale country, String... aliases);
	
	public Object getFileEntry(String fileAlias, Locale country, String key, Class<?> expected);
	
	public Charset getCharset();
	
	public Locale getDefaultLocale();
	
}

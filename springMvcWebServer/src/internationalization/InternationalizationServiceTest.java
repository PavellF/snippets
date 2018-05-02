package internationalization;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class InternationalizationServiceTest {

	@Test
	public void shouldLoadTextFilesFromFileSystem() {
		
		Map<String, String> pathAliasPairs = new HashMap<>();
		
		pathAliasPairs.put("WebContent/WEB-INF/internationalization/test.ru", "test"+Locale.RU);
		
		InternationalizationService localizationService = new InternationalizationServiceImp(pathAliasPairs,Locale.RU);
		
		Map<String, String> localizationFile = localizationService.getAllFileContent("test", Locale.RU);
		
		assertEquals("hello",localizationFile.get("hallo"));
		assertEquals("2+2=4",localizationService.getFileEntry("test", Locale.RU, "important", String.class));
		assertEquals("",localizationFile.get("empty"));
		assertEquals(true,localizationService.getFileEntry("test", Locale.RU, "bool", boolean.class));

	}

}

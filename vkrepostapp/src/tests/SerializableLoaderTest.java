package tests;

import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import objects.IterationPOJO;
import services.SerializableLoader;

public class SerializableLoaderTest {

	private static final String PATH = getPath();
	private static final String PATH_ARRAY = getPathArray();
	
	@Test
	public void test() {
		System.out.println("Expected path "+Paths.get(PATH).toAbsolutePath());
		
		SerializableLoader<IterationPOJO> serializableLoader
		= new SerializableLoader<IterationPOJO>(PATH);
		
		IterationPOJO newIteration = new IterationPOJO();
		newIteration.setAccess_token("token");
		
		serializableLoader.saveFile(newIteration);
		
		assert(Files.exists(Paths.get(PATH)));
		
		IterationPOJO newIterationLoaded = serializableLoader.loadFile();
		
		assertEquals(newIterationLoaded.getAccess_token(),"token");
	}
	
	@Test
	public void testArray(){
		
		System.out.println("Expected path "+Paths.get(PATH_ARRAY).toAbsolutePath());
		
		SerializableLoader<IterationPOJO[]> serializableLoader
		= new SerializableLoader<IterationPOJO[]>(PATH_ARRAY);
		
		IterationPOJO[] newIterationArray = new IterationPOJO[5];
		
		for(int i =0; i< newIterationArray.length; i++){
			IterationPOJO newIteration = new IterationPOJO();
			newIteration.setAccess_token("token"+i);
			newIterationArray[i] = newIteration;
		}
		
		serializableLoader.saveFile(newIterationArray);
		
		assert(Files.exists(Paths.get(PATH_ARRAY)));
		
		IterationPOJO[] newIterationArrayLoaded = serializableLoader.loadFile();
		
		assertEquals(newIterationArrayLoaded[0].getAccess_token(),"token0");
		
	}
	
	private static String getPath(){
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("user.dir"));
		sb.append("/test.case");
		return sb.toString();
	}
	
	private static String getPathArray(){
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("user.dir"));
		sb.append("/test.caseArray");
		return sb.toString();
	}

}

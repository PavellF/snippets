package util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AppUtilitiesTest {

	@Test
	public void testThisStringIsNumber() {
		boolean resultOne = AppUtilities.thisStringIsNumber("3.141");
		assertEquals(true,resultOne);
		
		boolean resultTwo = AppUtilities.thisStringIsNumber("3,141");
		assertEquals(false,resultTwo);
		
		boolean resultThree = AppUtilities.thisStringIsNumber("2D");
		assertEquals(true,resultThree);
	}

	@Test
	public void testIsThisArrayHasSpecificElement() {
		String[] title = {"Lady","Gaga","The","Fame","Monster"};
		boolean resultTrue = AppUtilities.isThisArrayHasSpecificElement("The", title);
		assertEquals(true,resultTrue);
		
		Integer[] set = {13,2,44,55,66,45,12};
		boolean resultFalse = AppUtilities.isThisArrayHasSpecificElement(2747, set);
		assertFalse(resultFalse);
	}

	@Test
	public void testFillThisArray() {
		Integer[][] array = new Integer[6][6];
		array = AppUtilities.fillThisArray(array, new Integer(10));
		
		assertSame(array[0][0],array[5][5]);
		
		for(int i = 0;i<6;i++){
			for(int j = 0; j<6;j++){
				assertEquals(10,array[i][j].intValue());
			}
		}
	}

	@Test
	public void testRemoveSpecificElementFromList() {
		List<String> list = new ArrayList<>();
		StringBuilder word = new StringBuilder();
		
		list.add("m");
		list.add("i");
		list.add("d");
		list.add("d");
		list.add("l");
		list.add("e");
		
		list = AppUtilities.removeSpecificElementFromList(list, "d");
		
		assertEquals(4,list.size());
		
		list.forEach((String val)->{word.append(val);});
		
		assertEquals("mile",word.toString());
	}
	
	@Test
	public void testStringListToString(){
		List<String> list = new ArrayList<>();
		list.add("Java");
		list.add("is");
		list.add("cool.");
		
		assertNotEquals(list.toString(),"Javaiscool.");
		
		String properToString = AppUtilities.stringListToString(list, "");
		
		assertEquals(properToString,"Javaiscool.");
	}

}

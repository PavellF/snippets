package theTemplateMethod;

import java.util.ArrayList;

/**
 * 
 *  The Template Method Pattern defines the skeleton of an algorithm 
 *  in a method, deferring some steps to subclasses. Template Method 
 *  lets subclasses redefine certain steps of an algorithm without 
 *  changing the algorithm’s structure.
 *
 **/

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		Algorithm fizzBuzz = new FizzBuzz();
		ArrayList<String> a = (ArrayList<String>) fizzBuzz.startExecuting(15);

		a.forEach((val) -> {
			System.out.println(val);
		});
		
		Algorithm anotherFizzBuzz = new AnotherFizzBuzz();
		ArrayList<String> b = (ArrayList<String>) 
				anotherFizzBuzz.startExecuting(15);
		
		b.forEach((val) -> {
			System.out.println(val);
		});
		
	}

}

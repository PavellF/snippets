package theTemplateMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Write a program that outputs the string representation of numbers from 1 to n.
 * But for multiples of three it should output “Fizz” instead of the number and
 * for the multiples of five output “Buzz”. For numbers which are multiples of  
 * both three and five output “FizzBuzz”.
 * 
 * https://leetcode.com/problems/fizz-buzz/description/
 * 
 **/

public class FizzBuzz extends Algorithm {

	@Override
	public void proto(Object input) {
		
		System.out.println("FizzBuzz algorithm has just started.");
	}
	
	@Override
	public Object computation(Object input) {
		
		final Integer untilNum = (Integer) input;
		final List<String> output = new ArrayList<>();
		
		for (int i = 1; i <= untilNum; i++) {
			
			final boolean byThree = check(i, 3);
			final boolean byFive = check(i, 5);
			
			if (byThree && byFive) {
				output.add("FizzBuzz");
			} else if (byFive) {
				output.add("Buzz");
			} else if (byThree) {
				output.add("Fizz");
			} else {
				output.add(String.valueOf(i));
			}
			
		}

       return output;
	}
	
	@Override
	public void post(Object input) { 
		
		System.out.println("FizzBuzz algorithm has just ended.");
		
	}
	
	private boolean check(int num, int by) {
		
		final int times = num / by;
		
		if(times == 0) {
			return false;
		}
		
		final long result = by * times;
		
		if (result == num) {
            return true;
        } else {
            return false;
        }
		
	}

}

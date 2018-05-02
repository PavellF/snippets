package theTemplateMethod;

import java.util.ArrayList;
import java.util.List;

public class AnotherFizzBuzz extends Algorithm {

	@Override
	protected Object computation(Object input) {
		
		final List<String> output = new ArrayList<>();
		final Integer n = (Integer) input;
		
		for(int i = 1; i <= n; i++) {
			
			if (i % 15 == 0) {
				output.add("FizzBuzz");
			} else if (i % 3 == 0) {
				output.add("Fizz");
			} else if (i % 5 == 0) {
				output.add("Buzz");
			} else {
				output.add(String.valueOf(i));
			}
			
		}
		
		return output;
		
	}

}

package util;

import org.springframework.stereotype.Component;

@Component
public class MathUtilities {

	public static double logGamma(double x) {
	      double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
	      double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
	                       + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
	                       +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
	      return tmp + Math.log(ser * Math.sqrt(2 * Math.PI));
	}
	
	public static double gamma(double x) { 
		return Math.exp(logGamma(x)); 
		
	}
	
	public static long factorial(long x){
		if(x<0) throw new IllegalArgumentException("Only positive numbers allowed.");
		long result = 1;
		if(x != 0){
		for(;x!=1;x--){
			result *= x;
		}
		}
		return result;
	}
	
	public static double getLogarithmWithAnyBase(double base,double number){
		if(base<0 || base == 1) 
			throw new MathLogicException("Base should be greater than zero and no equal 1.");
		if(number <= 0) 
			throw new MathLogicException("You should only take the Log of numbers that are greater than zero.");
		
		return Math.log(number) / Math.log(base);
	}
}

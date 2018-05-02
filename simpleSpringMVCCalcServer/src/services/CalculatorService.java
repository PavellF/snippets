package services;

import java.util.List;

import objects.UnitsOfMeasurement;

public interface CalculatorService {

	public String getAnswer(List<String> expression, UnitsOfMeasurement um);
	
}

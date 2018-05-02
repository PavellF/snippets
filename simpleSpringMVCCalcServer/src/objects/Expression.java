package objects;

import java.util.List;

public class Expression {

	private List<String> elements;
	private UnitsOfMeasurement measurement;
	private int complex;
	
	public Expression(List<String> elements,UnitsOfMeasurement measurement, int complex){
		this.elements = elements;
		this.measurement = measurement;
		this.complex = complex;
	}
	
	public Expression(){}

	public UnitsOfMeasurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(UnitsOfMeasurement measurement) {
		this.measurement = measurement;
	}

	public List<String> getElements() {
		return elements;
	}

	public void setElements(List<String> elements) {
		this.elements = elements;
	}

	public int getComplex() {
		return complex;
	}

	public void setComplex(int complex) {
		this.complex = complex;
	}

}

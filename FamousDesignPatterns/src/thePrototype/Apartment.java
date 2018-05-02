package thePrototype;

public class Apartment extends Unit {

	private double xPlace;
	private double yPlace;
	private double area;
	
	public Apartment() { }
	
	public Apartment(Apartment target) {
		super(target);
		if (target != null) {
			this.xPlace = target.xPlace;
			this.yPlace = target.yPlace;
			this.area = target.area;
		}
	}
	
	@Override
	public Unit clone() {
		return new Apartment(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(area);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(xPlace);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yPlace);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Apartment other = (Apartment) obj;
		if (Double.doubleToLongBits(area) != Double.doubleToLongBits(other.area))
			return false;
		if (Double.doubleToLongBits(xPlace) != Double.doubleToLongBits(other.xPlace))
			return false;
		if (Double.doubleToLongBits(yPlace) != Double.doubleToLongBits(other.yPlace))
			return false;
		return true;
	}

	public double getxPlace() {
		return xPlace;
	}

	public void setxPlace(double xPlace) {
		this.xPlace = xPlace;
	}

	public double getyPlace() {
		return yPlace;
	}

	public void setyPlace(double yPlace) {
		this.yPlace = yPlace;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

}

package thePrototype;

public class Client {

	public static void main(String[] args) {
		UnitCache cache = new UnitCache();
		
		Unit unit1 = cache.get("initial car");
		Unit unit2 = cache.get("initial car");
		Unit unit3 = cache.get("initial apartment");
		
		if (unit1 != unit2 && !unit1.equals(unit3) && unit1.equals(unit2)) {
            System.out.println("indentical");
        }
		
		Unit newUnit = new Car();
		newUnit.setName("Brand new car");
		Unit anotherUnit = newUnit.clone();
		System.out.println(anotherUnit == newUnit);

	}

}

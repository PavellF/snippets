package thePrototype;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UnitCache {
	private Map<String,Unit> cache;
	
	public UnitCache() {
		cache = new HashMap<>();
		init();
	}
	
	public Unit put(String key, Unit unit) {
		cache.put(key, unit);
		return unit;
	}
	
	public Unit get(String key) {
		return cache.get(key).clone();
	}
	
	private void init() {
		Car newCar = new Car();
		newCar.setName("Daimler");
		newCar.setPower(120);
		newCar.setPrice(new BigDecimal(12000));
		newCar.setStartDate(new Date());
		newCar.setType("SUV");
		
		Apartment newApartment = new Apartment();
		newApartment.setArea(120.0);
		newApartment.setName("Blue ouster");
		newApartment.setPrice(new BigDecimal(300000));
		newApartment.setStartDate(new Date());
		newApartment.setxPlace(56.877);
		newApartment.setyPlace(87.904);
		
		cache.put("initial car", newCar);
		cache.put("initial apartment", newApartment);
	}
}

package objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

public abstract class Domain {

	@Autowired
	private Gson gson;
	
	public String toJSON(){
		return gson.toJson(this);
	}
	
	
}

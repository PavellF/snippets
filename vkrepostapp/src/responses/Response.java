package responses;

import com.google.gson.annotations.SerializedName;

public abstract class Response <T>{
	
	@SerializedName("response")
	private T response;

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}
}

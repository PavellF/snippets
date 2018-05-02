package theBridge;

public abstract class DatabaseConnectivity {

	private boolean isUp;
	private int maxConnections;
	private String url;
	
	public DatabaseConnectivity(int maxConnections, String url) {
		this.maxConnections = maxConnections;
		this.url = url;
		isUp = false;
	}
	
	public boolean isUp() {
		return isUp;
	}

	public void setURL(String url) {
		this.url = url;

	}

	public String getURL() {
		return url;
	}

	public void setMaxConnections(int i) {
		this.maxConnections = i;

	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public abstract void printStatus();
	
	public abstract boolean connect();
	
	public abstract boolean disconnect();
	
}

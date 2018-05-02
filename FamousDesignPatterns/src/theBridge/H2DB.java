package theBridge;

public class H2DB extends DatabaseConnectivity {

	private boolean removeAllOldEntries;
	
	public H2DB(int maxConnections, String url, boolean removeAllOldEntries) {
		super(maxConnections, url);
		this.removeAllOldEntries = removeAllOldEntries;
	}

	@Override
	public void printStatus() {
		System.out.println(this.toString());

	}

	@Override
	public boolean connect() {
		System.out.println("Connecting to H2 database..");
		return true;
	}

	@Override
	public boolean disconnect() {
		System.out.println("Disconnecting from H2 database..");
		return true;
	}

	@Override
	public String toString() {
		return "H2DB [removeAllOldEntries=" + removeAllOldEntries + ", isUp()=" + isUp() + ", getURL()="
				+ getURL() + ", getMaxConnections()=" + getMaxConnections() + "]";
	}

}

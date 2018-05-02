package theBridge;

public class MySQLDB extends DatabaseConnectivity {

	public MySQLDB(int maxConnections, String url) {
		super(maxConnections, url);
	}

	@Override
	public void printStatus() {
		System.out.println(this.toString());
	}

	@Override
	public boolean connect() {
		
		if (doConnect()) {
			System.out.println("Connecting to "+super.getURL());
			return true;
		} else {
			System.out.println("Fail when connecting to "+super.getURL());
			return false;
		}
		
	}

	@Override
	public boolean disconnect() {
		
		if (doDisconnect()) {
			System.out.println("Disconnecting from "+super.getURL());
			return true;
		} else {
			System.out.println("Fail when disconnecting from "+super.getURL());
			return false;
		}
		
	}

	private boolean doConnect() {
		System.out.println("All work on connecting is here.");
		return true;
	}
	
	private boolean doDisconnect() {
		System.out.println("All work on disconnecting is here.");
		return true;
	}

	@Override
	public String toString() {
		return "MySQLDB [isUp()=" + isUp() + ", getURL()=" + getURL() + ", getMaxConnections()=" + getMaxConnections()
				+ "]";
	}

}

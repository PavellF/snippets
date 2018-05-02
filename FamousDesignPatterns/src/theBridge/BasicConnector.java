package theBridge;

public class BasicConnector implements Connector {

	protected DatabaseConnectivity databaseConnectivity;
	
	public BasicConnector() {}
	
	public BasicConnector(DatabaseConnectivity databaseConnectivity) {
		this.databaseConnectivity = databaseConnectivity;
	}
	
	@Override
	public void connect() {
		
		final boolean already = databaseConnectivity.isUp();
		
		if (!already) {
			databaseConnectivity.connect();
		} else {
			System.out.println("Already connected.");
		}

	}

	@Override
	public void setMaxConnections(int c) {
		databaseConnectivity.setMaxConnections(c);

	}

	@Override
	public void printStatus() {
		databaseConnectivity.printStatus();

	}

	@Override
	public void disconnect() {
		
		final boolean already = databaseConnectivity.isUp();
		
		if (already) {
			databaseConnectivity.disconnect();
		} else {
			System.out.println("Database is down.");
		}
	}

}

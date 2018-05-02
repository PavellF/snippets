package theBridge;

public class AdvancedConnector extends BasicConnector {

	public AdvancedConnector(DatabaseConnectivity databaseConnectivity) {
		super.databaseConnectivity = databaseConnectivity;
	}
	
	@Override
	public void setMaxConnections(int c) {
		
		if (c > 100) {
			System.out.println("This type of connection does not support more"
					+ " than 100 connections at same time.");
			return;
		}
		
		databaseConnectivity.setMaxConnections(c);

	}
	
}

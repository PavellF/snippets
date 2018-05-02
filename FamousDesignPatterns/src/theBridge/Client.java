package theBridge;

public class Client {

	public static void main(String[] args) {
	
		test(new MySQLDB(100, "sql:mysql.url"));
		test(new H2DB(100, "sql:mysql.url", false));
		
	}
	
	private static void test(DatabaseConnectivity dc) {
		
		Connector c1 = new BasicConnector(dc);
		c1.connect();
		c1.setMaxConnections(22);
		c1.printStatus();
		c1.disconnect();
		
		Connector c2 = new AdvancedConnector(dc);
		c2.connect();
		c2.setMaxConnections(120);
		c2.printStatus();
		c2.disconnect();
		
	}

}

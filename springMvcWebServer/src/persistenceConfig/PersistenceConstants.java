package persistenceConfig;

import util.Properties;

public class PersistenceConstants {

	public static final String H2_DRIVER = Properties.get().getString("H2_DRIVER");
	public static final String H2_CONNECTION_URL = Properties.get().getString("H2_URL");
	public static final String H2_USERNAME = Properties.get().getString("H2_USERNAME");
	public static final String H2_PASSWORD = Properties.get().getString("H2_PASSWORD");
	
	public static final String MYSQL_DRIVER = Properties.get().getString("MYSQL_DRIVER");
	public static final String MYSQL_CONNECTION_URL = Properties.get().getString("MYSQL_URL");
	public static final String MYSQL_USERNAME = Properties.get().getString("MYSQL_USERNAME");
	public static final String MYSQL_PASSWORD = Properties.get().getString("MYSQL_PASSWORD");
	
	//shows real SQL
	public static final String HIBERNATE_SHOW_SQL = Properties.get().getString("HIBERNATE_SHOW_SQL");
	/**
	 validate: validate the schema, makes no changes to the database.
	update: update the schema.
	create: creates the schema, destroying previous data.
	create-drop: drop the schema when the SessionFactory is closed explicitly, typically when the application is stopped.
	 */
	public static final String HIBERNATE_HBM2DLL_AUTO = Properties.get().getString("HIBERNATE_HBM2DLL_AUTO");
	public static final String HIBERNATE_DIALECT = Properties.get().getString("HIBERNATE_DIALECT");
	 
}

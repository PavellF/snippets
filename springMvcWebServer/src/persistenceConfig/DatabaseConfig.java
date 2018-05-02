package persistenceConfig;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

@Configuration
public class DatabaseConfig {

	private static final Logger logger = LogManager.getLogger(DatabaseConfig.class.getName());

	
	@Profile("h2")
	@Bean(name="dataSource")
	public DataSource singleConnectionDataSource() throws InterruptedException{
		SingleConnectionDataSource scds = new SingleConnectionDataSource();
		scds.setDriverClassName(PersistenceConstants.H2_DRIVER);
		scds.setUrl(PersistenceConstants.H2_CONNECTION_URL);
		scds.setUsername(PersistenceConstants.H2_USERNAME);
		scds.setPassword(PersistenceConstants.H2_PASSWORD);
		scds.setSuppressClose(true);
		logger.info("You are whithin dev profie");
		return scds;
	}
	
	@Profile("mysql")
	@Bean(name="dataSource")
	public DataSource driverManagerDataSource() throws InterruptedException{
		DriverManagerDataSource dmds = new DriverManagerDataSource();
		dmds.setDriverClassName(PersistenceConstants.MYSQL_DRIVER);
		dmds.setUrl(PersistenceConstants.MYSQL_CONNECTION_URL);
		dmds.setUsername(PersistenceConstants.MYSQL_USERNAME);
		dmds.setPassword(PersistenceConstants.MYSQL_PASSWORD);
		logger.info("You are whithin prod profie");
		return dmds;
	}
	
}

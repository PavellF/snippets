package config;

import javax.sql.DataSource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import util.Properties;

@Configuration
public class DatabaseConfig {

	private static final Logger logger = LogManager.getLogger(DatabaseConfig.class.getName());

	@Profile("h2")
	@Bean(name="dataSource")
	public DataSource singleConnectionDataSource() throws InterruptedException{
		SingleConnectionDataSource scds = new SingleConnectionDataSource();
		scds.setDriverClassName("org.h2.Driver");
		scds.setUrl(Properties.get().getString("H2_URL"));
		scds.setUsername(Properties.get().getString("H2_USERNAME"));
		scds.setPassword(Properties.get().getString("H2_PASSWORD"));
		scds.setSuppressClose(true);
		logger.info("H2 data source is loaded");
		return scds;
	}
	
	@Profile("mysql")
	@Bean(name="dataSource")
	public DataSource driverManagerDataSource() throws InterruptedException{
		DriverManagerDataSource dmds = new DriverManagerDataSource();
		dmds.setDriverClassName("com.mysql.jdbc.Driver");
		dmds.setUrl(Properties.get().getString("MYSQL_URL"));
		dmds.setUsername(Properties.get().getString("MYSQL_USERNAME"));
		dmds.setPassword(Properties.get().getString("MYSQL_PASSWORD"));
		logger.info("MYSQL data source is loaded");
		return dmds;
	}
	
}

package persistenceConfig;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;

import objects.Friend;
import objects.Message;
import objects.Notification;
import objects.User;


@org.springframework.context.annotation.Configuration
public class HibernateConfig {
	
	private static final Logger logger = LogManager.getLogger(HibernateConfig.class.getName());
	
	public Configuration getMyConfiguration(){
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Friend.class);
		configuration.addAnnotatedClass(Notification.class);
		configuration.addAnnotatedClass(Message.class);
		
		configuration.setProperty("hibernate.dialect", PersistenceConstants.HIBERNATE_DIALECT);
        configuration.setProperty("hibernate.connection.driver_class", PersistenceConstants.H2_DRIVER);
        configuration.setProperty("hibernate.connection.url", PersistenceConstants.H2_CONNECTION_URL);
        configuration.setProperty("hibernate.connection.username", PersistenceConstants.H2_USERNAME);
        configuration.setProperty("hibernate.connection.password", PersistenceConstants.H2_PASSWORD);
        configuration.setProperty("hibernate.show_sql", PersistenceConstants.HIBERNATE_SHOW_SQL);
        configuration.setProperty("hibernate.hbm2ddl.auto", PersistenceConstants.HIBERNATE_HBM2DLL_AUTO);
        return configuration;
	}
	
	
	public void printConnectionInfo(SessionFactory sessionFactory){
		Session session = sessionFactory.openSession();
			session.doWork(connection1 -> {
				logger.info("DB name: "+connection1.getMetaData().getDatabaseProductName());
				logger.info("DB version: "+connection1.getMetaData().getDatabaseProductVersion());
				logger.info("driver: "+connection1.getMetaData().getDriverName());
				logger.info("Autocommit: "+connection1.getAutoCommit());
			});
		session.close();
	}
	
	@Bean(name="sessionFactory")
	public SessionFactory createSessionFactory(){
		
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
		ssrb.applySettings(this.getMyConfiguration().getProperties());
		ServiceRegistry serviseRegistry = ssrb.build();
		SessionFactory sessionFactory = this.getMyConfiguration().buildSessionFactory(serviseRegistry);
		printConnectionInfo(sessionFactory);
		return sessionFactory;
	}
}

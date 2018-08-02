package org.pavelf.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Pre-employment test application.
 * @see <a href=https://github.com/revkov/JAVA.CSV.TEST>Description</a>
 * @author Pavel F.
 * @since 1.0
 */
@SpringBootApplication
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.run(args);

	}
	
	

}

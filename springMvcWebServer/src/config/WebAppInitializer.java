package config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import internationalization.InternationalizationConfig;
import persistenceConfig.DatabaseConfig;
import persistenceConfig.HibernateConfig;
import security.SecurityConfig;
import security.UserDetailsServiceImpl;
import util.Properties;



public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	public static final String MULTIPART_RESOLVER_LOCATION = "A:/workspacemars/springmvcwebcalc/multipartresolver";
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {
			RootConfig.class,
			HibernateConfig.class,
			DatabaseConfig.class,
			UserDetailsServiceImpl.class,
			InternationalizationConfig.class,
			SecurityConfig.class,
			};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	protected void customizeRegistration(Dynamic registration){
		registration.setMultipartConfig(new MultipartConfigElement(MULTIPART_RESOLVER_LOCATION,2097152,4194304,0));
		
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		
		Properties.instance(servletContext.getRealPath("META-INF/prop.properties"));
		
		servletContext.setInitParameter("spring.profiles.active", Properties.get().getString("SPRING_DB_PROFILE_ACTIVE"));
	}
}

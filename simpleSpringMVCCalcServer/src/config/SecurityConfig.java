package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import util.Properties;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	protected void configure(HttpSecurity http) throws Exception{
		http
		.authorizeRequests()
			.antMatchers("/stsocsc").hasAuthority("root")
			.anyRequest().permitAll()
		.and()
			.requiresChannel()
			.antMatchers("/").requiresInsecure()
		.and()
			.formLogin()
			.successForwardUrl("/login")
		.and()
			.rememberMe()
			.tokenValiditySeconds(9000)
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")
		.and()
			.httpBasic();
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth
		.inMemoryAuthentication()
		.withUser(Properties.get().getString("SPRING_SECURITY_IN_MEMORY_DB_ROOT_LOGIN"))
		.password(Properties.get().getString("SPRING_SECURITY_IN_MEMORY_DB_ROOT_PASSWORD"))
		.authorities("root");
	}
}

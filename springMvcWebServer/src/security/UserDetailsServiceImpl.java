package security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import enums.Roles;
import exceptions.NonexistentDomainException;
import internationalization.Locale;
import objects.User;
import util.HTTPUtils;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private DataSource dataSource;
	
	private final static String TABLE_NAME = "USERS";
	private final static String SQL_SELECT_EXPRESSION_BY_EMAIL = "SELECT * FROM "+TABLE_NAME+" WHERE email=?;";
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		ThreadPrincipalContainer tpc = null;
		User user = null;
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		if(!email.isEmpty()){
			
			user = findByEmail(email);
			
			if(user != null){
				
				authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
				
				tpc = new ThreadPrincipalContainer(authorities,user);
				
			}else{
				throw new NonexistentDomainException("Requested user does not exist.");
			}
			
		}else{
			throw new UsernameNotFoundException("Can not find user with given empty email. Login is unavailable.");
		}
		
		return tpc;
	}
	
	private User findByEmail(String email) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try{
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(SQL_SELECT_EXPRESSION_BY_EMAIL);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				user = new User();
				user.setAbout(resultSet.getString("ABOUT"));
				user.setBlocked(resultSet.getBoolean("BLOCKED"));
				user.setEmail(resultSet.getString("EMAIL"));
				user.setId(resultSet.getLong("ID"));
				user.setLocale((Locale) HTTPUtils.parseEnum(resultSet.getString("LOCALE"), Locale.values()));
				user.setPassword(resultSet.getString("PASSWORD"));
				user.setPopularity(resultSet.getLong("POPULARITY"));
				user.setRating(resultSet.getLong("RATING"));
				user.setRegistrationDate(resultSet.getTimestamp("REGISTRATION_DATE"));
				user.setRole((Roles) HTTPUtils.parseEnum(resultSet.getString("ROLE"), Roles.values()));
				user.setUsername(resultSet.getString("USERNAME"));
				user.setUserPicURL(resultSet.getString("USERPICURL"));
			}
			
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			
			try{
				if(resultSet != null) resultSet.close(); 
				
				if(preparedStatement != null) preparedStatement.close();
				
				if(connection != null) connection.close();
				
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
			
		}
		return user;
	}

}

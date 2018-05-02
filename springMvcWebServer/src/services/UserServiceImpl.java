package services;

import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import enums.Roles;
import exceptions.UserDoesNotExistException;
import objects.SignUpForm;
import objects.User;
import persistence.UsersRepository;
import util.Properties;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository userRepository;
	
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class.getName());
	
	@Override
	public User getUserById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public User getUserByName(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public long save(SignUpForm form) {
		
		final String generatedPassword = 
				new StandardPasswordEncoder().encode(form.getPassword());
		
		User user = new User();
		user.setBlocked(false);
		user.setEmail(form.getEmail());
		user.setPassword(generatedPassword);
		user.setRegistrationDate(new Date());
		user.setRole(Roles.USER);
		user.setUsername(form.getUsername());
		user.setUserPicURL(Properties.get().getString("DEFAULT_USER_PICTURE_URL"));
		
		long id = userRepository.persistUser(user);
		
		logger.info("String representation of newly created User domain -> "+user.toString());
		
		return id;
	}

	@Override
	public boolean increasePopularity(long userID) {
		
		User user = getUserById(userID);
		
		user.setPopularity(user.getPopularity()+1);
		
		return userRepository.update(user);
	}

	@Override
	public List<User> getAllWithId(List<Long> ids) {
		logger.info("All users with given ids has been successfully fetched from db.");
		return this.getAllWithId(ids);
	}

}

package services;

import java.util.List;

import objects.SignUpForm;
import objects.User;

public interface UserService {

	public User getUserById(long id);
	
	public User getUserByName(String username);
	
	public User getUserByEmail(String email);
	
	public long save(SignUpForm form);
	
	public boolean increasePopularity(long userID);
	
	public List<User> getAllWithId(List<Long> ids);
}

package persistence;

import java.util.List;

import objects.User;

public interface UsersRepository {

	public long persistUser(User user);
	
	public User findById(long id);
	
	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	public List<User> getAll();
	
	public boolean delete(Long id);
	
	public boolean update(User user);
	/*
	public boolean doesUserExist(String username);
	
	public boolean doesEmailExist(String email);
	*/
	public List<User> getAllWithId(List<Long> ids);
}

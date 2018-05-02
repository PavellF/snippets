package theProxy.protection;

public interface UserRepository {

	public User fetchById(long id);
	
	public long save(User user);
	
	public void update(User user);
	
	public void deleteUser(User user);
	
}

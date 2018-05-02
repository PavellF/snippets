package theProxy.protection;


public class UserRepositoryImpl implements UserRepository {

	@Override
	public User fetchById(long id) {
		System.out.println("Fetching user by id: "+id);
		return new User("John");
	}

	@Override
	public long save(User user) {
		System.out.println("Saving new user into DB: "+user);
		return 0;
	}

	@Override
	public void update(User user) {
		System.out.println("Updating existing user: "+user);

	}

	@Override
	public void deleteUser(User user) {
		System.out.println("Deleting user: "+user);

	}

}

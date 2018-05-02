package theProxy.protection;

public class GuestUserRepositoryInvocationHandler implements UserRepository {

	private final UserRepository userRepository;
	
	public GuestUserRepositoryInvocationHandler(UserRepository ur) {
		userRepository = ur;
	}

	@Override
	public User fetchById(long id) {
		return userRepository.fetchById(id);
	}

	@Override
	public long save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void update(User user) {
		System.err.println("Access is denied!");
		throw new IllegalAccessException();
	}

	@Override
	public void deleteUser(User user) {
		System.err.println("Access is denied!");
		throw new IllegalAccessException();
	}
	
}

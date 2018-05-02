package theProxy.protection;

public class SuperUserRepositoryInvocationHandler implements UserRepository {

	private final UserRepository userRepository;
	
	public SuperUserRepositoryInvocationHandler(UserRepository ur) {
		userRepository = ur;
	}
	
	@Override
	public User fetchById(long id) {
		return userRepository.fetchById(id);
	}

	@Override
	public long save(User user) {
		System.err.println("Access is denied!");
		throw new IllegalAccessException();
	}

	@Override
	public void update(User user) {
		userRepository.update(user);

	}

	@Override
	public void deleteUser(User user) {
		userRepository.deleteUser(user);

	}

}

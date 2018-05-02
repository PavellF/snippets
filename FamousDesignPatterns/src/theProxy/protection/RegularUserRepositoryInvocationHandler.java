package theProxy.protection;

//use factory to hide proxy creation instead of real subject creation
public class RegularUserRepositoryInvocationHandler implements UserRepository {

	private final UserRepository userRepository;
	private User principal;
	
	public RegularUserRepositoryInvocationHandler(UserRepository ur, User principal) {
		userRepository = ur;
		this.principal = principal;
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
		
		if (principal.equals(user)) {
			userRepository.update(user);
		} else {
			System.err.println("Access is denied!");
			throw new IllegalAccessException();
		}
	
	}

	@Override
	public void deleteUser(User user) {
		System.err.println("Access is denied!");
		
	}

}

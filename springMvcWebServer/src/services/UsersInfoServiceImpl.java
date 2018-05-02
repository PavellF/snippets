package services;

import org.springframework.stereotype.Component;

import objects.FixedSizeQueue;

@Component
public class UsersInfoServiceImpl implements UsersInfoService {

	private FixedSizeQueue<String> lastLoggedIn = new FixedSizeQueue<String>(10);
	private FixedSizeQueue<String> lastSignUp = new FixedSizeQueue<String>(10);
	
	@Override
	public void addNewLastLoggedIn(String username) {
		lastLoggedIn.add(username);
	}

	@Override
	public void addNewLastRegistered(String username) {
		lastSignUp.add(username);
	}

	@Override
	public FixedSizeQueue<String> getAllWhoLoggedIn() {
		return lastLoggedIn;
	}

	@Override
	public FixedSizeQueue<String> getAllWhoRegistered() {
		return lastSignUp;
	}

}

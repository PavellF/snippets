package services;

import objects.FixedSizeQueue;

public interface UsersInfoService {

	public void addNewLastLoggedIn(String username);
	public void addNewLastRegistered(String username);
	public FixedSizeQueue<String> getAllWhoLoggedIn();
	public FixedSizeQueue<String> getAllWhoRegistered();
}

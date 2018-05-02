package services;

import java.util.List;

import org.hibernate.HibernateException;

import enums.Order;
import enums.SortBy;
import enums.Status;
import objects.Friend;
import objects.User;


public interface FriendService {

	public List<Friend> getUserFriends(long userId, int skip, int count, boolean mutual, 
			SortBy sortBy, Order order) throws HibernateException;
	
	public Status addToFriends(User suggested, long askerId);
	
	public Status deleteFromFriends(long fromUser, long forUser);
	
	public Status acceptFriend(long suggested, long askerId);
	
	public Long getCount(long userId) throws HibernateException;
}

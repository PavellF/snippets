package persistence;

import java.util.List;

import org.hibernate.HibernateException;

import enums.Order;
import enums.SortBy;
import objects.Friend;

public interface FriendsRepository {

	public List<Friend> getUserFriends(long userId, int skip, int count, boolean mutual, 
			SortBy sortBy, Order order) throws HibernateException;
	
	public boolean createRelation(Friend first, Friend second) throws HibernateException ;
	
	public boolean createEntry(Friend friend) throws HibernateException ;
	
	public boolean deleteEntry(Friend friend) throws HibernateException;
	
	public boolean deleteRelation(Friend first, Friend second) throws HibernateException;
	
	public List<Friend> readEntry(long userID, long friendID) throws HibernateException;
	
	public List<Friend> readRelation(long userID, long friendID) throws HibernateException;
	
	public Long getCount(long userId) throws HibernateException;
	
	public Long isRelationExist(long userID, long friendID) throws HibernateException;
}

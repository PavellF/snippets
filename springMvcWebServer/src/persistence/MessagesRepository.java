package persistence;

import java.util.List;

import org.hibernate.HibernateException;

import enums.Order;
import enums.OriginType;
import enums.SortBy;
import objects.Message;

public interface MessagesRepository {

	public enum Archived{
		IGNORE,
		DELETED,
		NOT_DELETED
	}
	
	public List<Message> getAllMessagesForUser(long userID, Archived archived, SortBy sort, Order order, int count, int skip)
	throws HibernateException;
	
	public List<Message> getAllMessages(OriginType originType, long originID, 
			Archived archived, SortBy sort, Order order,int count, int skip) throws HibernateException;
	
	public long create(Message message) throws HibernateException;
	
	public boolean update(Message message) throws HibernateException;
	
	public boolean delete(Message message) throws HibernateException;
	
	public Message read(long id) throws HibernateException;
	
	public Long getCount(long userID, Archived archived) throws HibernateException;
	
	public Long getCount(OriginType originType, long originID, Archived archived) throws HibernateException;
}

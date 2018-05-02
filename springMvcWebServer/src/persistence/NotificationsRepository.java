package persistence;

import java.util.List;

import org.hibernate.HibernateException;

import enums.NotificationType;
import enums.Order;
import objects.Notification;

public interface NotificationsRepository {

	public Long getCountForUser(long userID) throws HibernateException;
	
	public long saveNotification(Notification notification) throws HibernateException;
	
	public Notification getNotification(long id) throws HibernateException;
	
	public boolean updateNotification(Notification notification) throws HibernateException;
	
	public List<Notification> getSelectedNotifications(List<Long> ids) throws HibernateException;
	
	public boolean updateSelectedNotifications(List<Notification> ids) throws HibernateException;
	
	public List<Notification> getAllNotificationsForUser(long userId, int skip,int count,boolean expired,
			NotificationType[] notificationType,Order order) throws HibernateException;
	
	public List<Notification> getAllNotificationsForUser(
			long userId, int skip, int count, Order order) throws HibernateException;
}

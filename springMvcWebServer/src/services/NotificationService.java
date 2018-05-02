package services;

import java.util.List;

import enums.NotificationType;
import enums.Order;
import enums.Status;
import objects.Notification;

public interface NotificationService {

	public Notification send(long by, long forUser, NotificationType notificationType, String content);
	
	public Status expire(List<Long> id, long ownerId);
	
	public List<Notification> getAllNotificationsForUser(long userId, int skip,int count,boolean expired,
			NotificationType[] notificationType,Order order);
	
	public List<Notification> getAllNotificationsForUser(long userId, int skip, int count, Order order);
	
	public Long getCountForUser(long userID);
	
	
}

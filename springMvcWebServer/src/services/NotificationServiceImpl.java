package services;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import enums.NotificationType;
import enums.Order;
import enums.Status;
import objects.Notification;
import persistence.NotificationsRepository;

@Component
public class NotificationServiceImpl implements NotificationService {

	private NotificationsRepository notificationsRepository;
	
	private static final Logger logger = LogManager.getLogger(NotificationServiceImpl.class.getName());
	
	@Inject
	public NotificationServiceImpl (NotificationsRepository notificationsRepository){
		this.notificationsRepository = notificationsRepository;
	}
	
	@Override
	public Notification send(long by, long forUser, NotificationType notificationType, String content) {
		
		Notification n = new Notification();
		n.setByUserID(by);
		n.setContent(content);
		n.setDate(new Date());
		n.setExpired(false);
		n.setForUser(forUser);
		n.setType(notificationType);
		
		long id = notificationsRepository.saveNotification(n);
		
		if(id < 0){
			logger.error("Fail while saving new notification into db. NULL is returning..");
			return null;
		}
		
		n.setId(id);
		
		logger.info("New notification was created. String representation is "+n.toString());
		
		return n;
	}

	@Override
	public Status expire(List<Long> id, long ownerId) {
		
		List<Notification> list = this.notificationsRepository.getSelectedNotifications(id);
		
		list.forEach((val) -> {
			
			if(val.getForUser() == ownerId) val.setExpired(true);
			
		});
		
		if(this.notificationsRepository.updateSelectedNotifications(list)){
			logger.info("Notifications was expired. ");
			return Status.SUCCESS;
		}else{
			logger.info("Notifications was NOT expired.");
			return Status.EXCEPTION;
		}
	}

	@Override
	public List<Notification> getAllNotificationsForUser(long userId, int skip, int count, boolean expired,
			NotificationType[] notificationType, Order order) {
		return this.notificationsRepository.getAllNotificationsForUser(userId, skip, count, expired, notificationType, order);
	}

	@Override
	public Long getCountForUser(long userID) {
		return this.notificationsRepository.getCountForUser(userID);
	}

	@Override
	public List<Notification> getAllNotificationsForUser(long userId, int skip, int count, Order order) {
		return this.notificationsRepository.getAllNotificationsForUser(userId, skip, count, order);
	}
	
}

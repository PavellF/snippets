package messagingServices;

import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import objects.Notification;

@Component
public class NotificationFeedServiceImpl implements NotificationFeedService {

	private SimpMessageSendingOperations messaging;
	private static final Logger logger = LogManager.getLogger(NotificationFeedServiceImpl.class.getName());
	
	@Autowired
	private Gson gson;
	
	@Inject
	public NotificationFeedServiceImpl(SimpMessageSendingOperations messaging) {
		this.messaging = messaging;
	}
	
	@Override
	public void broadcastNotification(Notification notification, String username) {
		
		this.messaging.convertAndSendToUser(username, "/queue/notifications", gson.toJson(notification));
		
		logger.info("Notification sended. String representation is -> "+notification.toString());
		
	}

}

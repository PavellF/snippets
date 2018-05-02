package messagingServices;

import objects.Notification;

public interface NotificationFeedService {
	
	public void broadcastNotification(Notification notification, String username);
	
}

package theAdapter;

public class NotificationManager {

	private MessageServiceSecured messageService;
	
	public NotificationManager(MessageServiceSecured messageService) {
		this.messageService = messageService;
	}
	
	public void sendToAll() {
		messageService.dispatch("/mainQueue");
	}
	
	public void handle() {
		messageService.receive("/mainQueue");
	}
	
	public void sendToAllSecure() {
		messageService.dispatchSecure("/mainQueue");
	}
	
}

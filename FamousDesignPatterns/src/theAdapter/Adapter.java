package theAdapter;

public class Adapter implements MessageServiceSecured {

	private MessageService messageService;
	
	public Adapter(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public void dispatch(String path) {
		messageService.sendMessage(path);
		
	}

	@Override
	public void receive(String path) {
		messageService.receiveMessage(path);
		
	}

	@Override
	public void dispatchSecure(String path) {
		
		throw new UnsupportedOperationException();
		
	}
	
	
}

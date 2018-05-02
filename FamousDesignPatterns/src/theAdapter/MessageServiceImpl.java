package theAdapter;

public class MessageServiceImpl implements MessageService {

	@Override
	public void sendMessage(String path) {
		
		System.out.println("Sending to " + path);

	}

	@Override
	public void receiveMessage(String path) {
		
		System.out.println("Receiving from " + path);

	}

}

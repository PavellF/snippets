package theAdapter;

public class MessageServiceSecuredImpl implements MessageServiceSecured {

	@Override
	public void dispatch(String path) {
		
		System.out.println("Dispatching to " + path);

	}

	@Override
	public void receive(String path) {
		
		System.out.println("Receiving from " + path);

	}

	@Override
	public void dispatchSecure(String path) {
		
		System.out.println("Dispatch encoded to " + path);

	}

}

package theAdapter;

public interface MessageServiceSecured {

	public void dispatch(String path);
	
	public void receive(String path);
	
	public void dispatchSecure(String path);
	
	
}

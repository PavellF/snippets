package theBridge;

public interface Connector {

	public void connect();
	
	public void disconnect();
	
	public void setMaxConnections(int c);
	
	public void printStatus();
	
}

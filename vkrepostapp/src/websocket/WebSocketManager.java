package websocket;

public interface WebSocketManager {

	public void sendMessage(String message);
	public void add(ApplicationWebSocket aws);
	public void remove(ApplicationWebSocket aws);
}

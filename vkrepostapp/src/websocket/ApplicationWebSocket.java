package websocket;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class ApplicationWebSocket {

	private Session session;
	private WebSocketManager webSocketManager;
	static final Logger logger = LogManager.getLogger(ApplicationWebSocket.class.getName());

	 
	public ApplicationWebSocket(WebSocketManager webSocketManager){
		this.webSocketManager = webSocketManager;
	}
	
	@OnWebSocketConnect
	public void onOpen(Session session){
		webSocketManager.add(this);
		this.session = session;
	}
	
	@OnWebSocketMessage
	public void onMessgae(String message){
		webSocketManager.sendMessage(message);
	}
	
	@OnWebSocketClose
	public void onClose(int statusCode, String reason){
		webSocketManager.remove(this);
	}
	
	public void sendString(String message){
		try{
			session.getRemote().sendString(message);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
	}

}

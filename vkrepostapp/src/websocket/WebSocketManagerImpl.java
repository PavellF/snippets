package websocket;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebSocketManagerImpl implements WebSocketManager{

	private Set<ApplicationWebSocket> webSockets;
	static final Logger logger = LogManager.getLogger(WebSocketManagerImpl.class.getName());
	private static WebSocketManagerImpl webSocketManagerImpl;
	
	public static WebSocketManagerImpl instance(){
		if(webSocketManagerImpl == null)
			webSocketManagerImpl = new WebSocketManagerImpl();
		
		return webSocketManagerImpl;
	}
	
	private WebSocketManagerImpl(){
		this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
	}
	
	public void sendMessage(String message){ 
		
		for(ApplicationWebSocket socket : webSockets){
			try{
				socket.sendString(message);
			}catch(Exception e){
				logger.error(e.getMessage());
			}
		}
	}
		
	public void add(ApplicationWebSocket aws){
			webSockets.add(aws);
	}
		
	public void remove(ApplicationWebSocket aws){
			webSockets.remove(aws);
	}
}

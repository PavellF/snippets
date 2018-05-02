package websocket;

import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;



@SuppressWarnings("serial")
@WebServlet(name = "log", urlPatterns = {"/log"})
public class LoggerWebSocketServlet extends WebSocketServlet {

	private static final int LOGOUT_TIME = 10*60*1000;
	public static final String PAGE_URL = "/log";
	private final WebSocketManager webSocketManager;
	private static LoggerWebSocketServlet loggerWebSocketServlet;
	
	private LoggerWebSocketServlet(WebSocketManager webSocketManager){
		this.webSocketManager = webSocketManager;
	}
	
	public static LoggerWebSocketServlet instance(WebSocketManager webSocketManager){
		if(loggerWebSocketServlet == null)
			loggerWebSocketServlet = new LoggerWebSocketServlet(webSocketManager);
		return loggerWebSocketServlet;
	}
	
	@Override
	public void configure(WebSocketServletFactory arg0) {
		
		arg0.getPolicy().setIdleTimeout(LOGOUT_TIME);
		arg0.setCreator((req, resp) -> new ApplicationWebSocket(webSocketManager));
		
	}

	public void send(String message) {
		this.webSocketManager.sendMessage(message);
		
	}
	
	
	
	
}

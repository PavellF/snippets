package websocket;

public class GetLogger {

	public static LoggerWebSocketServlet getwsLogger(){
		final WebSocketManager WS_MANAGER = WebSocketManagerImpl.instance();
		LoggerWebSocketServlet loggerWebSocketServlet = LoggerWebSocketServlet.instance(WS_MANAGER);
		return loggerWebSocketServlet;
	}
}

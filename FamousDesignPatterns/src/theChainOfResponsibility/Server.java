package theChainOfResponsibility;

import java.util.HashMap;
import java.util.Map;

public class Server {

	private static Map<String, String> users = new HashMap<>();
    private Middleware middleware;
	
    public void setMiddleware(Middleware middleware) {
        this.middleware = middleware;
    }
    
	public static void rememberMe(String login) {
		 System.out.println("Remembered: "+login);
	}
	
	public static boolean hasLogin(String login) {
		return users.containsKey(login);
	}
	
	public void register(String email, String password) {
        users.put(email, password);
    }
	
	public boolean logIn(Object data) {
        if (middleware.check(data)) {
            System.out.println("Authorization has been successful!");
            return true;
        }
        return false;
    }
	
}

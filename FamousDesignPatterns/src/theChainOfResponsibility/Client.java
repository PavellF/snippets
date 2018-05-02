package theChainOfResponsibility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Server server;
	
	private static void init() {
        server = new Server();
        server.register("admin", "1234");
        server.register("user", "qwerty");

        Middleware middleware = new ThrottlingMiddleware(2);
        middleware
        			.chain(new UserExistsMiddleware())
                .chain(new RoleCheckMiddleware())
                .chain(new RememberMeMiddleware());

        server.setMiddleware(middleware);
    }

    public static void main(String[] args) throws IOException {
        init();

        boolean success;
        do {
            System.out.print("Enter login:\n");
            String login = reader.readLine();
            
            System.out.print("Input password:\n");
            String password = reader.readLine();
            
            System.out.print("Remember me: true or false\n");
            Boolean remember = Boolean.parseBoolean(reader.readLine());
            
            success = server.logIn(new LoginForm(login, password, remember));
        } while (!success);
        
        
    }
	
	
}

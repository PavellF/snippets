package theChainOfResponsibility;

public class RoleCheckMiddleware extends Middleware {

	@Override
	public boolean check(Object data) {
		
		final LoginForm form = (LoginForm) data;
		
		if (form.getLogin().equals("admin")) {
            System.out.println("Hello, admin!");
            return true;
        }
        System.out.println("Hello, user!");
        return checkNext(data);
		
	}

}

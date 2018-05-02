package theChainOfResponsibility;

public class UserExistsMiddleware extends Middleware {

	@Override
	public boolean check(Object data) {
		
		final LoginForm form = (LoginForm) data;
		
		if(!Server.hasLogin(form.getLogin())) {
			return false;
		}
		
		return checkNext(data);
	}

}

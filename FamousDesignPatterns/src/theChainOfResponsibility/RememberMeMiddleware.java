package theChainOfResponsibility;

public class RememberMeMiddleware extends Middleware {

	@Override
	public boolean check(Object data) {
		
		final LoginForm form = (LoginForm) data;
		
		if (form.isRememberMe()) {
			Server.rememberMe(form.getLogin());
		}
		
		return checkNext(data);
	}

}

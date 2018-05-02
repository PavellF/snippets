package theChainOfResponsibility;

public abstract class Middleware {

	private Middleware next;
	
	public Middleware chain(Middleware next) {
		this.next = next;
		return next;
	}
	
	public abstract boolean check(Object data);
	
	protected boolean checkNext(Object data) {
        if (next == null) {
            return true;
        }
        return next.check(data);
    }
	
}

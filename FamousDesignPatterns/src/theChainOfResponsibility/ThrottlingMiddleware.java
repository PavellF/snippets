package theChainOfResponsibility;

public class ThrottlingMiddleware extends Middleware {

	private int requestPerMinute;
	private int requests;
	private long currentTime;
	
	public ThrottlingMiddleware(int requestPerMinute) {
		this.requestPerMinute = requestPerMinute;
		this.currentTime = System.currentTimeMillis();
	}
	
	/**
	 * 
	 * hasNext() can be inserted at the beginning of the method, in the middle,
	 * at the end.
	 * Element of chain can let all others checks go instead of it first
	 * and do its check in the end, after all others do.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean check(Object data) {
		
		if (System.currentTimeMillis() > currentTime + 60_000) {
            requests = 0;
            currentTime = System.currentTimeMillis();
        }

        requests++;
        
        if (requests > requestPerMinute) {
            System.out.println("Request limit exceeded!");
            Thread.currentThread().stop();
        }
        return checkNext(data);
	}

}

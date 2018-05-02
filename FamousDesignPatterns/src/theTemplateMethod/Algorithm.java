package theTemplateMethod;

import java.util.Date;

public abstract class Algorithm {

	public final Object startExecuting(Object input, Object inputProto, Object post) {
		
		final Date start = startTime();
		proto(inputProto);
		
		Object results = computation(input);
		
		post(post);
		endTime(start);
		
		return results;
		
	}
	
	public final Object startExecuting(Object input) {
		
		final Date start = startTime();
		proto(null);
		
		Object results = computation(input);
		
		post(null);
		endTime(start);
		
		return results;
	}
	
	protected final Date startTime() {
		
		final Date start = new Date();
		System.out.println("Sarted at "+ start.toString());
		return start;
		
	}
	
	protected void proto(Object input) {
		
		System.out.println("Client is feel free to override this method.");
	}
	
	protected abstract Object computation(Object input);
	
	protected void post(Object input) { 
		
		System.out.println("Client is feel free to override this method.");
		
	}
	
	protected final Date endTime(Date start) {
		
		final Date end = new Date();
		System.out.println("Ended at "+ end.toString());
		
		final long overall = end.getTime() - start.getTime();
		System.out.println("Mills passed "+ overall);
		
		return end;
		
	}
	
}

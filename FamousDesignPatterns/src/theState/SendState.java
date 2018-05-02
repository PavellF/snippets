package theState;

public class SendState implements State {

	private BuyingProcessor buyingProcessor;
	
	public SendState(BuyingProcessor buyingProcessor) {
		this.buyingProcessor = buyingProcessor;
	}

	@Override
	public void ordered(Object data) {
		System.out.println("Item is already ordered.");
		
	}

	@Override
	public void moneyRecieved(Object data) {
		System.out.println("Money is already recieved.");
		
	}

	@Override
	public void refused(Object data) {
		System.out.println("Order is cancelled. Money is returned.");
		buyingProcessor.refuse(data);
		buyingProcessor.setState(buyingProcessor.getNoOrderedState());
		
	}

	@Override
	public void sendedToCustomer(Object data) {
		System.out.println("Going to send...");
		
	}

	@Override
	public void recievedByCustomer(Object data) {
		
		System.out.println("Going to send...");
	}

	@Override
	public void wait(Object data) {
		
		System.out.println("Item is sended to customer.");
		buyingProcessor.setState(buyingProcessor.getOnWayState());
	}

	@Override
	public void acceptedByCustomer(Object data) {
		System.out.println("Going to send...");
		
	}

}

package theState;

public class OnWayState implements State {

	private BuyingProcessor buyingProcessor;
	
	public OnWayState(BuyingProcessor buyingProcessor) {
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
		System.out.println("Cannot refuse until customer receive item.");

	}

	@Override
	public void sendedToCustomer(Object data) {
		System.out.println("Already sended.");

	}

	@Override
	public void recievedByCustomer(Object data) {
		System.out.println("Item is recieved by customer.");
		buyingProcessor.setState(buyingProcessor.getRecieveState());
	}


	@Override
	public void wait(Object data) {
		System.out.println("On the way.");
		
	}


	@Override
	public void acceptedByCustomer(Object data) {
		System.out.println("On the way.");
		
	}

}

package theState;

public class NoOrderedState implements State {

	private BuyingProcessor buyingProcessor;
	
	public NoOrderedState(BuyingProcessor buyingProcessor) {
		this.buyingProcessor = buyingProcessor;
	}

	@Override
	public void ordered(Object data) {
		System.out.println("Item is ordered by cusomer.");
		buyingProcessor.setState(buyingProcessor.getOrderState());
	}

	@Override
	public void moneyRecieved(Object data) {
		System.out.println("Item has not ordered yet!");
		
	}

	@Override
	public void refused(Object data) {
		System.out.println("Item has not ordered yet!");
		
	}

	@Override
	public void sendedToCustomer(Object data) {
		System.out.println("Item has not ordered yet!");
		
	}

	@Override
	public void recievedByCustomer(Object data) {
		System.out.println("Item has not ordered yet!");
		
	}

	@Override
	public void wait(Object data) {
		
		System.out.println("Item has not ordered yet!");
	}

	@Override
	public void acceptedByCustomer(Object data) {
		System.out.println("Item has not ordered yet!");
		
	}

}

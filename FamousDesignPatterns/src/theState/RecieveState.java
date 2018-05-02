package theState;

public class RecieveState implements State {

	private BuyingProcessor buyingProcessor;
	
	public RecieveState(BuyingProcessor buyingProcessor) {
		this.buyingProcessor = buyingProcessor;
	}

	@Override
	public void ordered(Object data) {
		System.out.println("Already ordered.");
		
	}

	@Override
	public void moneyRecieved(Object data) {
		System.out.println("Money is already recieved.");
		
	}

	@Override
	public void refused(Object data) {
		buyingProcessor.refuse(data);
		System.out.println("Customer doesn't like item.");
		buyingProcessor.setState(buyingProcessor.getNoOrderedState());
	}

	@Override
	public void sendedToCustomer(Object data) {
		System.out.println("Already sended.");
		
	}

	@Override
	public void recievedByCustomer(Object data) {
		System.out.println("Already recieved.");
	}

	@Override
	public void wait(Object data) {
		System.out.println("Already recieved.");
		
	}

	@Override
	public void acceptedByCustomer(Object data) {
		
		final int q = buyingProcessor.getQuantity();
		
		if (q > 0) {
			System.out.println("Processing is fully complete.");
			buyingProcessor.setState(buyingProcessor.getNoOrderedState());
		} else {
			System.out.println("It was last item.");
			buyingProcessor.setState(buyingProcessor.getSoldOutState());
		}
	}
	
}

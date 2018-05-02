package theState;

public class BoughtState implements State {

	private BuyingProcessor buyingProcessor;
	
	public BoughtState(BuyingProcessor buyingProcessor) {
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
		System.out.println("Order is cancelled.");
		buyingProcessor.setState(buyingProcessor.getNoOrderedState());
	}

	@Override
	public void sendedToCustomer(Object data) {
		buyingProcessor.buy(data, false);
		System.out.println("Money processed, item is going to send to customer.");
		buyingProcessor.setState(buyingProcessor.getSendState());
	}

	@Override
	public void recievedByCustomer(Object data) {
		System.out.println("Item has not been sended to customer.");

	}


	@Override
	public void wait(Object data) {
		System.out.println("Item has not been sended to customer.");
		
	}


	@Override
	public void acceptedByCustomer(Object data) {
		System.out.println("Item has not been sended to customer.");
	}

}

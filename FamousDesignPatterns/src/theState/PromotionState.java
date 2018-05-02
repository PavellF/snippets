package theState;

public class PromotionState implements State {

	private BuyingProcessor buyingProcessor;
	
	
	public PromotionState(BuyingProcessor buyingProcessor) {
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
		System.out.println("Cannot refuse promotional item.");
	}

	@Override
	public void sendedToCustomer(Object data) {
		buyingProcessor.buy(data, true);
		System.out.println("Free, item sended to customer.");
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

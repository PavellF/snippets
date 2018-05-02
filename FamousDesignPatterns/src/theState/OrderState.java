package theState;

import java.util.Random;

public class OrderState implements State {

	private BuyingProcessor buyingProcessor;
	private final Random randomWinner = new Random(System.currentTimeMillis());
	
	public OrderState(BuyingProcessor buyingProcessor) {
		this.buyingProcessor = buyingProcessor;
	}
	
	@Override
	public void ordered(Object data) {
		System.out.println("Item is already ordered.");
	}

	@Override
	public void moneyRecieved(Object data) {
		System.out.println("Go to paying.");
		int winner = randomWinner.nextInt(10);
		
		if (winner == 0) {
			buyingProcessor.setState(buyingProcessor.getPromotionState());
		} else {
			buyingProcessor.setState(buyingProcessor.getBoughtState());
		}
	}

	@Override
	public void refused(Object data) {
		System.out.println("Order is refused.");
		buyingProcessor.setState(buyingProcessor.getNoOrderedState());
	}

	@Override
	public void sendedToCustomer(Object data) {
		System.out.println("Order is issued, money have not been prosessed.");
	}

	@Override
	public void recievedByCustomer(Object data) {
		System.out.println("Money have not been prosessed.");
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

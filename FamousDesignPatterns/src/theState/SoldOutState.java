package theState;

public class SoldOutState implements State {

	@Override
	public void ordered(Object data) {
		System.out.println("ordered: Sold out!");
		
	}

	@Override
	public void moneyRecieved(Object data) {
		System.out.println("moneyRecieved: Sold out!");

	}

	@Override
	public void refused(Object data) {
		System.out.println("refused: Sold out!");

	}

	@Override
	public void sendedToCustomer(Object data) {
		System.out.println("sendedToCustomer: Sold out!");

	}

	@Override
	public void recievedByCustomer(Object data) {
		System.out.println("recievedByCustomer: Sold out!");

	}

	@Override
	public void wait(Object data) {
		System.out.println("wait: Sold out!");
		
	}

	@Override
	public void acceptedByCustomer(Object data) {
		System.out.println("acceptedByCustomer: Sold out!");
		
	}

}

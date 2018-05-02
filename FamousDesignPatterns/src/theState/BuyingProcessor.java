package theState;

import java.math.BigDecimal;

public class BuyingProcessor implements Operations {

	// these fields may be static then state will be common for all 
	// BuyingProcessor instances
	private final State boughtState;
	private final State noOrderedState;
	private final State orderState;
	private final State recieveState;
	private final State sendState;
	private final State soldOutState;
	private final State promotionState;
	private final State onWayState;
	
	private State currentState;
	private final Unit unit;
	private int quantity;
	
	public BuyingProcessor(Unit unit, int quantity) {
		
		this.boughtState = new BoughtState(this);
		this.noOrderedState = new NoOrderedState(this);
		this.orderState = new OrderState(this);
		this.recieveState = new RecieveState(this);
		this.sendState = new SendState(this);
		this.soldOutState = new SoldOutState();
		this.promotionState = new PromotionState(this);
		this.onWayState = new OnWayState(this);
		this.unit = unit;
		this.quantity = quantity;
		this.currentState = (quantity > 0) ? noOrderedState : soldOutState;
		
	}
	
	public void setState(State state) {
		this.currentState = state;
	}
	
	public void buy(final Object customer, boolean prom) {
		
		Customer cust = (Customer) customer;
		final BigDecimal before = cust.getMoney();
		final BigDecimal after = (prom) ? before : before.subtract(unit.getPrice());
		
		if (after.signum() < 0) {
			System.err.println("Cannot process! Need more money.");
			return;
		}
		
		quantity = quantity - 1;
		cust.setMoney(after);

	}
	
	public void refuse(final Object customer) {
		
		Customer cust = (Customer) customer;
		quantity = quantity + 1;
		cust.setMoney(cust.getMoney().add(unit.getPrice()));
	}
	
	public void refillItems(int count) {
		quantity += count;
		currentState = noOrderedState;
	}

	public State getBoughtState() {
		return boughtState;
	}

	public State getNoOrderedState() {
		return noOrderedState;
	}

	public State getOrderState() {
		return orderState;
	}

	public State getRecieveState() {
		return recieveState;
	}

	public State getSendState() {
		return sendState;
	}

	public State getSoldOutState() {
		return soldOutState;
	}

	public Object getUnit() {
		return unit;
	}

	public int getQuantity() {
		return quantity;
	}

	public State getPromotionState() {
		return promotionState;
	}
	

	public void order(Customer customer) {
		currentState.ordered(customer);
	}
	
	public void pay(Customer customer) {
		currentState.moneyRecieved(customer);
	}
	
	public void refuse(Customer customer) {
		currentState.refused(customer);
	}
	
	public void sendToCustomer(Customer customer) {
		currentState.sendedToCustomer(customer);
	}
	
	public void recieveItem(Customer customer) {
		currentState.recievedByCustomer(customer);
	}

	@Override
	public String toString() {
		return "BuyingProcessor [quantity=" + quantity + "]";
	}

	public State getOnWayState() {
		return onWayState;
	}

	@Override
	public void pending(Customer customer) {
		currentState.wait(customer);
	}

	@Override
	public void acceptItem(Customer customer) {
		currentState.acceptedByCustomer(customer);
		
	}
	
	
}

package modelViewController;

import javafx.stage.Stage;

public class ControllerInterfaceImpl implements ControllerInterface {

	private final View view;
	private final RemoteOrderManager remoteModel;
	private final OrderManager model;
	
	public ControllerInterfaceImpl(RemoteOrderManager remoteModel, OrderManager model, Stage ps) {
		this.view = new View(ps, this);
		this.remoteModel = remoteModel;
		this.model = model;
		
		model.registerObserver(view);
		remoteModel.registerObserver(view);
	}
	
	@Override
	public void doRemoteRequest() {
		view.renderAll(remoteModel.getRemotes());
	}

	@Override
	public void place(Order order) {
		model.placeOrder(order);
	}

	@Override
	public void deleteOrder(String name) {
		model.deleteOrderByName(name);

	}

	@Override
	public void updateAll() {
		view.rerenderAll(model.getAllOrders());
	}

}

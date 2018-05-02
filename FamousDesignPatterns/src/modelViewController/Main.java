package modelViewController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		OrderManager om = new OrderManagerImpl();
		RemoteOrderManager rom = new RemoteOrderManagerImpl();
		
		ControllerInterface ci = new ControllerInterfaceImpl(rom, om, primaryStage);
		
		primaryStage.setTitle("Order manager");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

}

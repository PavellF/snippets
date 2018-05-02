package applicationGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public interface GUI <T,A>{

	public T getGUI(EventHandler<ActionEvent> action, ControlPanel<A> mainPane);
}

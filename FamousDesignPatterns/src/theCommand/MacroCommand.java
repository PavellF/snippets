package theCommand;

import java.util.List;

public class MacroCommand<T> implements Command<T> {

	private List<Command<T>> list;

	public MacroCommand(List<Command<T>> commands) {
		list = commands;
	}
	
	@Override
	public void execute(T byValue) {
		
		list.forEach((val) -> {
			val.execute(byValue);
		});
		
	}

	@Override
	public void undo() {
		
		list.forEach((val) -> {
			val.undo();
		});
	}

	@Override
	public void store() {
		
		list.forEach((val) -> {
			val.store();
		});
		
	}

	@Override
	public void load() {
		
		list.forEach((val) -> {
			val.load();
		});
		
	}
	
	

}

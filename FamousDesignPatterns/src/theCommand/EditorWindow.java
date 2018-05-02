package theCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;

public class EditorWindow {

	@SuppressWarnings("rawtypes")
	private final List<Command> commands;
	@SuppressWarnings("rawtypes")
	private Queue<Command> undo;
	
	public EditorWindow() {
		
		commands = new ArrayList<>();
		undo = new LinkedTransferQueue <>();
		
	}
	
	public <T> void setActionPanelButtons(Command<T> command) {
		commands.add(command);
	}
	
	public void undo() {
		
		Command unCommand = undo.poll();
		
		if (unCommand != null) {
			unCommand.undo();
		} 
		
	}
	
	public <T> void onClick(int no, T type) {
		
		if (no < commands.size()) {
			@SuppressWarnings("unchecked")
			final Command<T> command = commands.get(no);
			
			command.execute(type);
			command.store();
			undo.add(command);
		}
		
	}
	
	public void loadAll() {
		
		commands.forEach((val) -> {
			val.load();
		});
		
		undo.forEach((val) -> {
			val.load();
		});
		
	}

	@Override
	public String toString() {
		
		final StringBuilder sb = 
				new StringBuilder("EditorWindow [\n");
		
		commands.forEach((val) -> {
			
			sb.append("Button ");
			sb.append(val.getClass().getName());
			sb.append("\n");
			
		});
		
		sb.append("Undo button ");
		sb.append(undo.getClass().getName());
		sb.append("\n]\n");
		sb.append("queue size ");
		sb.append(undo.size());
		
		
		return sb.toString();
	}
	
}

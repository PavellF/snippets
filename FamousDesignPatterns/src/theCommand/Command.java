package theCommand;

public interface Command<T> {

	public void execute(T byValue);
	
	public void undo();
	
	public void store();
	
	public void load();
	
}

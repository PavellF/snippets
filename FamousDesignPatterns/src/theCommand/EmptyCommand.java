package theCommand;

public class EmptyCommand implements Command<Object> {

	@Override
	public void execute(Object o) { }

	@Override
	public void undo() { }

	@Override
	public void store() { }

	@Override
	public void load() { }

}

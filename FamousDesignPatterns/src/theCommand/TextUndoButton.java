package theCommand;

@SuppressWarnings("rawtypes")
public class TextUndoButton implements Command {

	private final WriterService writerService;
	private String lastPart;
	
	public TextUndoButton(WriterService writerService) {
		this.writerService = writerService;
		lastPart = "";
	}
	
	@Override
	public void execute(Object byValue) {
		
		lastPart = writerService.getLastEnteredCharSeq();
		writerService.erase();
		
	}

	@Override
	public void undo() {
		
		writerService.appendToText(lastPart);
		
	}

	@Override
	public void store() {
		System.out.println("Logic for saving part of text to local storage.");
		
	}

	@Override
	public void load() {
		System.out.println("Logic for loading part of text from local storage.");
		
	}



}

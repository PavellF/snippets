package theCommand;

@SuppressWarnings("rawtypes")
public class BoldClearButtonLogic implements Command {

	private final EditorService editorService;
	
	public BoldClearButtonLogic(EditorService editorService) {
		this.editorService = editorService;
	}
	
	@Override
	public void execute(Object byValue) {
		
		editorService.clearBold();
		
	}

	@Override
	public void undo() {
		
		editorService.bold();
		
	}

	@Override
	public void store() {
		System.out.println("*logic to store object on local storage*");
	}

	@Override
	public void load() { 
		System.out.println("*logic to load object from local storage*");
	}



}

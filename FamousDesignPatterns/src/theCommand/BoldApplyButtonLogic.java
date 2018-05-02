package theCommand;

@SuppressWarnings("rawtypes")
public class BoldApplyButtonLogic implements Command {


	private final EditorService editorService;
	
	public BoldApplyButtonLogic(EditorService editorService) {
		this.editorService = editorService;
	}
	
	@Override
	public void execute(Object byValue) {
		
		editorService.bold();
		
	}

	@Override
	public void undo() {
		
		editorService.clearBold();
		
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

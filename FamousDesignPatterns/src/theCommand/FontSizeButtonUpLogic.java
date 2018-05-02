package theCommand;

public class FontSizeButtonUpLogic implements Command<Integer> {

	private final FontSizeService editorService;
	private int pervious;
	
	public FontSizeButtonUpLogic(FontSizeService editorService) {
		this.editorService = editorService;
	}
	
	@Override
	public void execute(Integer val) {
		
		pervious = val;
		editorService.fontSizeUp(val);
		
	}

	@Override
	public void undo() {
		
		editorService.fontSizeDown(pervious);
		
	}

	@Override
	public void store() {
		
		System.out.println("Logic for persisting current size to local storage."
				+ "(must implement Serializable)");
		
	}

	@Override
	public void load() {
		
		System.out.println("Logic for loading size to local storage.");
		
	}

}

package theCommand;

public class EditorServiceImpl implements EditorService {

	@Override
	public void bold() {
		
		System.out.println("Text has just set to bold.");

	}

	@Override
	public void clearBold() {
		
		System.out.println("Text is clear from bold now.");

	}

}

package theCommand;

public class FontSizeServiceImpl implements FontSizeService {

	private int size;
	
	public FontSizeServiceImpl() {
		size = 0;
	}
	
	@Override
	public void fontSizeUp(int value) {
		
		size = size + value;
		System.out.println("fontSizeUp by "+value+" now is "+size);
		
	}

	@Override
	public int getFontSize() {
		return size;
	}

	@Override
	public void fontSizeDown(int value) {
		
		size = size - value;
		System.out.println("fontSizeDown by "+value+" now is "+size);
		
	}



}

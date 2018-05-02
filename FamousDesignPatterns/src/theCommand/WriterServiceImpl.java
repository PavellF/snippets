package theCommand;

public class WriterServiceImpl implements WriterService {

	
	
	@Override
	public String getLastEnteredCharSeq() {
		
		return "Last entered text here";

	}

	@Override
	public void erase() {
		
		System.out.println("*Erases given textual data from text"
				+ " until this is equals to the text. Starts from the end.*");

	}

	@Override
	public void appendToText(String seq) {
		
		System.out.println("*Appends given textual data to text*");
		
	}

}

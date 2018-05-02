package theCommand;

public interface WriterService {

	public String getLastEnteredCharSeq();
	
	public void erase();
	
	public void appendToText(String seq);
	
}

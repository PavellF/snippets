package theProxy.virtual;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextBlock implements TextualFile {

	private StringBuffer text;
	
	public TextBlock(String path) {
		text = new StringBuffer(load(path));
	}
	
	@Override
	public String getText() {
		return text.toString();
	}

	@Override
	public String[] getWords() {
		return text.toString().split("[^a-zA-Z0-9А-Яа-яЁё]");
	}

	@Override
	public long getLength() {
		return text.length();
	}
	
	private String load(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
			return "";
		}
	}


}

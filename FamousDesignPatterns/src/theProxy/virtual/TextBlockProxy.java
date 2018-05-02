package theProxy.virtual;

public class TextBlockProxy implements TextualFile {

	private volatile TextualFile tf;
	private final String path;
	private Thread retrievalThread;
	private boolean retrieving = false;
	
	public TextBlockProxy(String path) {
		this.path = path;
	}
	
	synchronized private void setTextualFile(TextualFile textualFile) {
		tf = textualFile;
	}
	
	@Override
	public String getText() {
		
		if (tf != null) {
			return tf.getText();
		} else {
			
			if (!retrieving) {
				retrieving = true;

				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
							setTextualFile(new TextBlock(path));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				retrievalThread.start();
			}
			
			return "File is loading.";
		}
		
	}

	@Override
	public String[] getWords() {
		
		if (tf != null) {
			return tf.getWords();
		} else {
			return new String[] {"empty"};
		}
		
	}

	@Override
	public long getLength() {
		if (tf != null) {
			return tf.getLength();
		} else {
			return -1;
		}
	}


}

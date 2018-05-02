package theProxy.virtual;

import java.util.Arrays;

//virtual proxy can help to load expensive to instantiate classes
public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		TextualFile tf = new TextBlockProxy("text.txt");
		System.out.println(tf.getText());
		System.out.println(tf.getLength());
		
		Thread.sleep(1000);
		
		Arrays.asList(tf.getWords()).forEach((val) -> {
			System.out.println(val);
		});
		
		System.out.println(tf.getText());
		System.out.println(tf.getLength());
		
	}

}

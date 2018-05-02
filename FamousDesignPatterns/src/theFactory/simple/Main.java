package theFactory.simple;

public class Main {

	public static void main(String[] args) {
		
		SimpleButtonFactory sbf = new SimpleButtonFactory();
		DisplayElementProducer dep 
		= new DisplayElementProducer(sbf);
		
		Button btn = dep.produceButton(ButtonType.BUTTON_NEXT,
				"Go next");
		
		btn.onClick();
		System.out.println(btn.getLabel());

	}

}

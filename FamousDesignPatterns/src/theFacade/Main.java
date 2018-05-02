package theFacade;

import theFacade.subsystem.*;

/**
 * 
 * The Facade Pattern provides a unified interface 
 * to a set of interfaces in a subsytem. Facade 
 * defines a higherlevel interface that makes 
 * the subsystem easier to use.
 * 
 **/

public class Main {

	public static void main(String[] args) {
		
		ImageManager im = new ImageManagerImpl(
				new ImageProcessorImpl(),
				new ImageRepositoryImpl(),
				new ImageValidatorImpl(),
				new MessagesRepositoryImpl(),
				new MessageValidatorImpl(),
				new NotificationServiceImpl());

		im.uploadImageOnServer();
		System.out.println("");
		im.addCommentaryToImage();
		System.out.println("");
		im.updateImage();
		
	}

}

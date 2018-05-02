package theFacade;

import theFacade.subsystem.*;

public class ImageManagerImpl implements ImageManager {

	private ImageProcessor imageProcessor;
	private ImageRepository imageRepository;
	private ImageValidator imageValidator;
	private MessagesRepository messagesRepository;
	private MessageValidator messageValidator;
	private NotificationService notificationService;
	
	public ImageManagerImpl(ImageProcessor imageProcessor, ImageRepository imageRepository,
			ImageValidator imageValidator, MessagesRepository messagesRepository, MessageValidator messageValidator,
			NotificationService notificationService) {
		super();
		this.imageProcessor = imageProcessor;
		this.imageRepository = imageRepository;
		this.imageValidator = imageValidator;
		this.messagesRepository = messagesRepository;
		this.messageValidator = messageValidator;
		this.notificationService = notificationService;
	}

	@Override
	public void uploadImageOnServer() {
		
		imageValidator.validate();
		imageProcessor.resizeImage();
		imageRepository.saveImageOnDisk();
		
		messageValidator.validateMessage();
		messagesRepository.postMessage();

		notificationService.sendNotificationToAllFriends();
		
	}

	@Override
	public void addCommentaryToImage() {
		
		messageValidator.validateMessage();
		messagesRepository.postMessage();

		notificationService.sendNotificationToAllFriends();

	}

	@Override
	public void updateImage() {
		
		imageValidator.validate();
		imageProcessor.resizeImage();
		imageRepository.saveImageOnDisk();

	}

}

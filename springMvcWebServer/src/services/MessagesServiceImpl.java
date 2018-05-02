package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import controllers.Aliases;
import enums.NotificationType;
import enums.Order;
import enums.OriginType;
import enums.SortBy;
import enums.Status;
import exceptions.AccessDeniedException;
import exceptions.NonexistentDomainException;
import internationalization.InternationalizationService;
import messagingServices.MessageFeedService;
import messagingServices.NotificationFeedService;
import objects.Message;
import objects.Notification;
import objects.User;
import persistence.MessagesRepository;
import persistence.MessagesRepository.Archived;

@Component
public class MessagesServiceImpl implements MessagesService {

	private MessagesRepository messagesRepository;
	
	@Autowired
	private MessageFeedService messageFeedService;
	
	@Autowired
	private NotificationFeedService notificationFeedService;
	
	@Autowired 
	private InternationalizationService internationalizationService;
	
	@Autowired
	private NotificationService notificationService;
	
	private final static int ALLOWED_LENGTH_OF_MESSAGE_IN_NOTIFICATION = 128;
	
	private static final Logger logger = LogManager.getLogger(MessagesServiceImpl.class.getName());
	
	@Inject
	public MessagesServiceImpl(MessagesRepository messagesRepository){
		this.messagesRepository = messagesRepository;
	}
	
	@Override
	public List<Message> getAllMessagesForUser(long userID, Archived archived, SortBy sort, Order order, int count,
			int skip) {
		return messagesRepository.getAllMessagesForUser(userID, archived, sort, order, count, skip);
	}

	@Override
	public List<Message> getAllMessages(OriginType originType, long originID, Archived archived, SortBy sort,
			Order order, int count, int skip) {
		return messagesRepository.getAllMessages(originType, originID, archived, sort, order, count, skip);
	}

	@Override
	public Message createMessage(User author, String content, OriginType originType, long originID, long reply) {
		
		if(author.isBlocked()) {
			logger.error("User whose id is "+author.getId()+
					" was blocked and can not send or update messages. NULL is returning..");
			return null;
		}
		
		Message message = new Message();
		message.setArchived(false);
		message.setAuthor(author);
		message.setContent(content);
		message.setDate(new Date());
		message.setOriginId(originID);
		message.setOriginType(originType);
		message.setRating(0);
		
		if(reply > 0) {
			
			logger.info("Message is reply to message with id -> "+reply);
			
			Message m = this.messagesRepository.read(reply);
			
			if(m == null) {
				logger.error("Message user tried to reply does not exist. NULL is returning..");
				return null;
			}
			
			message.setReplied(reply);
			
			User originAuthor = m.getAuthor();
			final long originAuthorID = originAuthor.getId();
			
			if(originAuthorID != author.getId()){
			
			final StringBuffer notificationContent = new StringBuffer((String) 
					internationalizationService.getFileEntry
					(Aliases.NOTIFICATION_CONTENT, originAuthor.getLocale(), NotificationType.REPLY.toString(), String.class));
			
			notificationContent.append(" ");		
			
			if(content.length() < ALLOWED_LENGTH_OF_MESSAGE_IN_NOTIFICATION){
				
				notificationContent.append(content);
				
			}else{
				
				notificationContent.append(content.substring(0, ALLOWED_LENGTH_OF_MESSAGE_IN_NOTIFICATION));
				
			}
			
			Notification n = notificationService.send(
					author.getId(),
					originAuthorID,
					NotificationType.REPLY,
					notificationContent.toString());
			
			this.notificationFeedService.broadcastNotification(n, originAuthor.getUsername());
			}
		}
		
		final long id = this.messagesRepository.create(message);
		
		if(id > 0){
			
			message.setId(id);
			messageFeedService.broadcastMessage(message);
			logger.info("Message is successfully saved. String representation -> "+message.toString());
			
			return message;
		}else{
			logger.error("While processing message exception threw. Null status is going to return.");
			return null;
		}
	}
	
	@Override
	public Status changeMessageRating(long id, long value, User by) {
		
		Message m = this.messagesRepository.read(id);
		
		if(m == null) {
			logger.error("Message user tried to like does not exist. NULL is returning..");
			return Status.EXCEPTION;
		} 
		
		if(by.getId() == m.getAuthor().getId()){
			logger.error("User can not change own message rating.");
			return Status.EXCEPTION;
		}
		
		long rating = m.getRating();
		
		m.setRating(rating+value);
		
		if(this.messagesRepository.update(m)){
			
			logger.info("To rating of this message with id ["+id+"] was added a value -> "+value+" new rating is -> "+m.getRating());
			
			NotificationType notificationType = NotificationType.LIKE;
			String localContent = "";
			
			if(value > 0){
				
				localContent = (String) internationalizationService.getFileEntry
						(Aliases.NOTIFICATION_CONTENT, m.getAuthor().getLocale(), NotificationType.LIKE.toString(), String.class);
						
			}else{
				
				localContent = (String) internationalizationService.getFileEntry
						(Aliases.NOTIFICATION_CONTENT, m.getAuthor().getLocale(), NotificationType.DISLIKE.toString(), String.class);
			
				notificationType = NotificationType.DISLIKE;
			}
			
			final StringBuffer notificationContent = new StringBuffer(localContent);
					
			notificationContent.append(" ");
			
			if(m.getContent().length() < ALLOWED_LENGTH_OF_MESSAGE_IN_NOTIFICATION){
				
				notificationContent.append(m.getContent());
				
			}else{
				
				notificationContent.append(
						m.getContent().substring(0, ALLOWED_LENGTH_OF_MESSAGE_IN_NOTIFICATION));
				
			}
			
			Notification n = notificationService.send(
					by.getId(),
					m.getAuthor().getId(),
					notificationType,
					notificationContent.toString());
			
			this.notificationFeedService.broadcastNotification(n, m.getAuthor().getUsername());
			
			return Status.SUCCESS;
		}else{
			logger.error("While processing changing rating of message with id ["+id+"] an exception has been thrown.");
			return Status.EXCEPTION;
		}
		
	}

	@Override
	public Status changeMessageContent(User author ,long id, String newContent) {

		if(author.isBlocked()) {
			logger.error("User whose id is "+author.getId()+" was blocked and can not update messages.");
			return Status.ACCESS_DENIED;
		}
		
		Message m = this.messagesRepository.read(id);
		
		if(!m.getAuthor().equals(author)) {
			logger.error("This user can not change content of given message. This message belongs to other author.");
			return Status.ACCESS_DENIED;
		}
		
		m.setContent(newContent);
		m.setChanged(new Date());
		
		if(this.messagesRepository.update(m)){
			logger.info("Content of message with id ["+id+"] was seccessfully changed.");
			return Status.SUCCESS;
		}else{
			logger.info("Content of message with id ["+id+"] was not changed.");
			return Status.EXCEPTION;
		}
	}

	@Override
	public Status archiveMessage(User author ,long id) {

		if(author.isBlocked()) {
			logger.error("User whose id is "+author.getId()+" was blocked and can not delete messages.");
			return Status.ACCESS_DENIED;
		}
		
		Message m = this.messagesRepository.read(id);
		
		if(!m.getAuthor().equals(author)) {
			logger.error("This user can not change content of given message. This message belongs to other author.");
			return Status.ACCESS_DENIED;
		}
		
		m.setArchived(true);
		
		if(this.messagesRepository.update(m)){
			logger.info("This message with id ["+id+"] is archived.");
			return Status.SUCCESS;
		}else{
			logger.info("This message with id ["+id+"] is not archived.");
			return Status.EXCEPTION;
		}
	}

	@Override
	public Long getCount(long userID, Archived archived) {
		return this.messagesRepository.getCount(userID, archived);
	}

	@Override
	public Long getCount(OriginType originType, long originID, Archived archived) {
		return this.messagesRepository.getCount(originType, originID, archived);
	}

}

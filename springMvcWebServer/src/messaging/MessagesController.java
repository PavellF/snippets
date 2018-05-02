package messaging;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import enums.Status;
import objects.Message;
import objects.MessageForm;
import objects.UpdateMessageForm;
import objects.User;
import security.SecurityUtilities;
import security.ThreadPrincipalContainer;
import services.MessagesService;
import util.AppUtilities;
import util.HTMLParser;
import util.HTTPUtils;

@Controller
public class MessagesController {

	private static final Logger logger = Logger.getLogger(MessagesController.class);
	
	private enum Type{
		
		LIKE,
		DISLIKE,
		DELETE
		
	}
	
	@Autowired
	private MessagesService messagesService;
	
	@Autowired
	private Gson gson;
	
	@SubscribeMapping("/messaging/messageStream/post")
	public String handleMessage(SimpMessageHeaderAccessor accessor, Authentication authentication){
		
		String formJSON = accessor.getFirstNativeHeader("messageFormPayloadJSON");
		
		ThreadPrincipalContainer principal = SecurityUtilities.getThreadPrincipal(authentication);
		
		if(formJSON == null || principal == null){
			logger.error("Payload header or principal is null. Null is returning..");
			return null;
		}
		
		MessageForm form = gson.fromJson(formJSON, MessageForm.class);
		
		final int length = form.getContent().toCharArray().length;
		final String content = form.getContent();
		
		if(length < 1 || length > 1024) {
			logger.error("Incoming message is too short or long. Null is returning..");
			return null;
		}
		
		if(AppUtilities.isStringContainsOnlyWhitespaces(content)){
			logger.error("Incoming message contains only whitespaces. Null is returning..");
			return null;
		}
		
		logger.info("Received -> "+form.toString());
		
		User author = principal.getUser();
		
		if(author == null) {
			logger.error("User must auth before he will send message.");
			return null;
		}
		
		final String parsed = HTMLParser.get().parse(content);
		
		Message created = this.messagesService.createMessage(
				author, parsed, form.getOriginType(), form.getOriginId(), form.getReply());
		
		return gson.toJson(created);
	} 
	
	@SubscribeMapping("/messaging/messageStream/update")
	public Status handleRequestForUpdate(SimpMessageHeaderAccessor accessor, Authentication authentication){
		
		String formJSON = accessor.getFirstNativeHeader("messageUpdateFormPayload");
		
		ThreadPrincipalContainer principal = SecurityUtilities.getThreadPrincipal(authentication);
		
		if(formJSON == null || principal == null){
			logger.error("Payload header or principal is null. Null is returning..");
			return null;
		}
		
		UpdateMessageForm umf = gson.fromJson(formJSON, UpdateMessageForm.class);
		
		logger.info("Received -> "+umf.toString());
		
		final int length = umf.getNewContent().toCharArray().length;
		final String content = umf.getNewContent();
		
		if(length < 1 || length > 1024) {
			logger.error("Incoming message is too short or long. Null is returning..");
			return null;
		}
		
		if(AppUtilities.isStringContainsOnlyWhitespaces(content)){
			logger.error("Incoming message contains only whitespaces. Null is returning..");
			return null;
		}
		
		User author = principal.getUser();
		
		if(author == null) {
			logger.error("User must auth before he will send message.");
			return null;
		}
		
		final String parsed = HTMLParser.get().parse(content);
		
		return this.messagesService.changeMessageContent(author, umf.getMessageID(), parsed);
	}
	
	@SubscribeMapping("/messaging/messageStream/delete/{id}")
	public Status handleRequestForDelete(@DestinationVariable String id, Authentication authentication){
		return determine(id, authentication, Type.DELETE);
	}
	
	@SubscribeMapping("/messaging/messageStream/like/{id}")
	public Status handleLike(@DestinationVariable String id, Authentication authentication){
		return determine(id, authentication, Type.LIKE);
	}
	
	@SubscribeMapping("/messaging/messageStream/dislike/{id}")
	public Status handleDislike(@DestinationVariable String id, Authentication authentication){
		return determine(id, authentication, Type.DISLIKE);
	}
	
	private Status determine(String incoming, Authentication authentication, Type type){
		
		ThreadPrincipalContainer principal = SecurityUtilities.getThreadPrincipal(authentication);
		
		if(incoming == null) return Status.EXCEPTION;
		
		if(principal == null) return Status.ACCESS_DENIED;
		
		logger.info("User is requesting "+type+" operation. Received -> "+incoming);
		
		final Long messageID = HTTPUtils.parseID(incoming);
		
		if(messageID == null) return Status.EXCEPTION;
		
		logger.error("Message id -> "+messageID);
		
		User author = principal.getUser();
		
		if(author == null) return Status.ACCESS_DENIED;
		
		Status status = Status.EXCEPTION;
		
		switch(type){
		case DISLIKE: status = messagesService.changeMessageRating(messageID, -1, author);
			break;
		case LIKE: status = messagesService.changeMessageRating(messageID, 1, author);
			break;
		case DELETE: status = this.messagesService.archiveMessage(author, messageID);
			break;
		}
		
		return status;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

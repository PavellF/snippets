package messagingServices;

import javax.inject.Inject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import objects.Message;

@Component
public class MessageFeedServiceImpl implements MessageFeedService{

	private SimpMessageSendingOperations messaging;
	private static final Logger logger = LogManager.getLogger(MessageFeedServiceImpl.class.getName());
	
	@Autowired
	private Gson gson;
	
	@Inject
	public MessageFeedServiceImpl(SimpMessageSendingOperations messaging) {
		this.messaging = messaging;
	}
	
	@Override
	public void broadcastMessage(Message message) {
		
		final String currentPath = message.getOriginType().toString()+message.getOriginId();
		
		this.messaging.convertAndSend("/topic/messageStream"+currentPath, gson.toJson(message));
		
		logger.info("Message "+message.getContent()+" converted and sended.");
		
	}

}

package messaging;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import exceptions.AccessDeniedException;
import exceptions.BusinessLogicException;
import objects.User;
import security.SecurityUtilities;
import security.ThreadPrincipalContainer;
import services.NotificationService;
import util.HTTPUtils;

@Controller
public class NotificationsController {

	private static final Logger logger = Logger.getLogger(NotificationsController.class);
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private Gson gson;
	
	@MessageMapping(value="/messaging/expireNotification")
	public void handleIncoming(String incoming , Authentication authentication){
		
		ThreadPrincipalContainer principal = SecurityUtilities.getThreadPrincipal(authentication);
		
		if(incoming == null) throw new IllegalArgumentException("NULL received.");
		
		if(principal == null) throw new AccessDeniedException("Principal is not found.");
		
		logger.info("Expire notifications was requested.");
		
		List<Long> listOfIds = Arrays.asList((Long[]) gson.fromJson(incoming, Long[].class));
		
		if(listOfIds == null || listOfIds.size() == 0){
			throw new BusinessLogicException("List of notification ids is null or empty.");
		}
		
		if(listOfIds.size() > 6) throw new BusinessLogicException("List size of notification ids is out of allowed range.");
		
		this.notificationService.expire(listOfIds, principal.getUser().getId());
	}
	
}

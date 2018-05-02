package messaging;



import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import enums.Status;
import objects.User;
import security.SecurityUtilities;
import security.ThreadPrincipalContainer;
import services.FriendService;
import services.UserService;
import util.HTTPUtils;

@Controller
public class UserProfileController {

	private enum Operation {
		ADD,
		ACCEPT,
		DELETE
	}
	
	private static final Logger logger = Logger.getLogger(UserProfileController.class);
	
	@Autowired
	private FriendService friendService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Gson gson;
	
	@SubscribeMapping("/messaging/relationship/add/{id}")
	public String askForFriend(@DestinationVariable String id, Authentication authentication){
		return getAnswer(id, authentication, Operation.ADD);
	}
	
	@SubscribeMapping("/messaging/relationship/accept/{id}")
	public String acceptFriend(@DestinationVariable String id, Authentication authentication){
		return getAnswer(id, authentication, Operation.ACCEPT);
	}
	
	@SubscribeMapping("/messaging/relationship/discard/{id}")
	public String discardFriend(@DestinationVariable String id, Authentication authentication){
		return getAnswer(id, authentication, Operation.DELETE);
	}
	
	@SubscribeMapping("/messaging/relationship/delete/{id}")
	public String deleteFriend(@DestinationVariable String id, Authentication authentication){
		return getAnswer(id, authentication, Operation.DELETE);
	}
	
	@MessageMapping("/messaging/increasePopularity/{id}")
	public void increasePopularity(@DestinationVariable String id){
		
		if(id == null) throw new IllegalStateException("NULL received.");
		
		logger.info("Received: ".concat(id));
			
		Long userID = HTTPUtils.parseID(id);
		
		if(userID != null){
			userService.increasePopularity(userID);
		}
	}
	
	private String getAnswer(String incoming, Authentication authentication, Operation type){
		
		User principal = null;
		ThreadPrincipalContainer tpc = SecurityUtilities.getThreadPrincipal(authentication);
		
		if(incoming == null && tpc == null) {
			logger.error("Incoming is null or user who trying to request has no authentication. Exception status is returning.");
			return gson.toJson(Status.EXCEPTION);
		}else{
			principal = tpc.getUser();
		}
		
		if(principal == null || principal.isBlocked()){
				logger.error("User who sent a request was blocked or does not exist.");
				return gson.toJson(Status.ACCESS_DENIED);
		}
		
		logger.info("Received ID is "+incoming);
		
		final Long id = HTTPUtils.parseID(incoming);
		
		if(null == id){
			logger.error("Fail while parsing. Incoming id is not a number.");
			return gson.toJson(Status.EXCEPTION);
		} 
		
		logger.info(principal.getUsername()+" is requesting performing operation with type "+type);
		
		Status status = Status.EXCEPTION;
		final long principalID = principal.getId();
		
		switch(type){
		case ACCEPT: status = friendService.acceptFriend(principalID, id);
			break;
		case ADD: {
			User user = this.userService.getUserById(id);
			
			if(user == null || user.isBlocked()){
				logger.error("Can not add to friends unexistent or blocked user.");
				return gson.toJson(Status.ACCESS_DENIED);
			}
			
			status = friendService.addToFriends(user, principalID);
		} break;
		case DELETE: status = friendService.deleteFromFriends(principalID, id);
			break;
		}
		
		return gson.toJson(status);
		
	}
	
}

package rest;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import enums.NotificationType;
import enums.Order;
import enums.SortBy;
import exceptions.InternalServerException;
import exceptions.InvalidRequestParameterException;
import exceptions.NonexistentDomainException;
import objects.Friend;
import objects.Notification;
import objects.User;
import security.SecurityUtilities;
import services.FriendService;
import services.NotificationService;
import services.UserService;
import util.HTTPUtils;

@RestController
@RequestMapping(value="/api")
public class RESTUserController {

	@Autowired
	private Gson gson;
	
	@Autowired
	private FriendService friendService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private NotificationService notificationService;
	
	private static final Logger logger = LogManager.getLogger(RESTUserController.class.getName());
	
	@RequestMapping(value="/getUserFriends/{userId}",method=RequestMethod.GET,produces ="application/json; charset=UTF-8")
	public ResponseEntity<String> getAllUsersFriends(
			@RequestParam(value = "accepted", required = false) String accepted,
			@RequestParam(value = "count", required = false) String count, 
			@RequestParam(value = "sortBy", required = false) String sortBy, 
			@RequestParam(value = "order", required = false) String order, 
			@RequestParam(value = "skip", required = false) String skip,
			@PathVariable String userId){
		
		final long reqUserId = HTTPUtils.parseLong(userId);
		final boolean isPrincipal = SecurityUtilities.isPrincipal(reqUserId);
		
		logger.info("This is principal request -> "+isPrincipal);
		
		boolean acceptedVal = false;
		int countVal = 5;
		SortBy sortingBy = SortBy.SIGNUP_DATE;
		Order orderStyle = Order.DESCENDING;
		int skipVal = 0;
		
		if(order != null && order.equals("ASC")){
			logger.info("Incoming order param is not null. Its value is ASC");
			orderStyle = Order.ASCENDING;
		}
		
		if(sortBy != null){
			logger.info("Incoming sortBy param is not null. Its value is -> "+sortBy);
			
			sortingBy = (SortBy) HTTPUtils.parseEnum(sortBy, 
					SortBy.FRIENDSHIP_DATE,
					SortBy.NAME,
					SortBy.POPULARITY,
					SortBy.RATING);
			
			if(sortingBy == null) throw new InvalidRequestParameterException("Illegal value of sortBy param.");
		}
		
		if(accepted != null){
			logger.info("Incoming accepted param is not null. Its value is -> "+accepted);
			acceptedVal = HTTPUtils.parseBoolean(accepted);
		}
		
		if(count != null ) {
			logger.info("Incoming count param is not null. Its value is -> "+count);
			countVal = HTTPUtils.parseInteger(0,49,count);
		}
		
		if(skip != null ) {
			logger.info("Incoming skip param is not null. Its value is -> "+skip);
			skipVal = HTTPUtils.parseInteger(0,Integer.MAX_VALUE,skip);
		}
		
		List<Friend> list = (isPrincipal) ? 
				friendService.getUserFriends(reqUserId, skipVal, countVal, acceptedVal, sortingBy, orderStyle) :
					friendService.getUserFriends(reqUserId, skipVal, countVal, true, sortingBy, orderStyle);
		
		if(list == null) throw new InternalServerException("Internal server error.");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json; charset=UTF-8");
		headers.set("Accept-Charset", "utf-8");
		ResponseEntity<String> response = 
				new ResponseEntity<String>(gson.toJson(list),headers,HttpStatus.OK);
		return response;
	}
	
	
	@RequestMapping(value="/getUser/{username}",method=RequestMethod.GET,produces ="application/json; charset=UTF-8")
	public ResponseEntity<String> getUser(@PathVariable String username){
		String json = gson.toJson(userService.getUserByName(username));
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json; charset=UTF-8");
		headers.set("Accept-Charset", "utf-8");
		ResponseEntity<String> response = 
				new ResponseEntity<String>(json,headers,HttpStatus.OK);
		return response;
	}
	
	
	
	@RequestMapping(value="/getUserNotifications/{userId}", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
	public ResponseEntity<String> getUserNotifications(
			@RequestParam(value = "count", required = false) String count,
			@RequestParam(value = "expired", required = false) String expired,
			@RequestParam(value = "order", required = false) String order,
			@RequestParam(value = "skip", required = false) String skip,
			@PathVariable String userId){

		final long reqUserId = HTTPUtils.parseLong(userId);
		final boolean isPrincipal = SecurityUtilities.isPrincipal(reqUserId);
		
		logger.info("This is principal request -> "+isPrincipal);
		
		boolean expiredVal = false;
		int countVal = 5;
		Order orderStyle = Order.DESCENDING;
		int skipVal = 0;
		
		if(order != null && order.equals("ASC")){
			logger.info("Incoming order param is not null. Its value is ASC");
			orderStyle = Order.ASCENDING;
		}
		
		if(expired != null){
			logger.info("Incoming expired param is not null. Its value is -> "+expired);
			expiredVal = HTTPUtils.parseBoolean(expired);
		}
		
		if(count != null ) {
			logger.info("Incoming count param is not null. Its value is -> "+count);
			countVal = HTTPUtils.parseInteger(0,100,count);
		}
		
		if(skip != null ) {
			logger.info("Incoming skip param is not null. Its value is -> "+skip);
			skipVal = HTTPUtils.parseInteger(0,Integer.MAX_VALUE,skip);
		}
		
		NotificationType[] vals =  {NotificationType.REPLY,NotificationType.LIKE,NotificationType.DISLIKE};
		
		List<Notification> list = (isPrincipal) ?
				notificationService.getAllNotificationsForUser(reqUserId, skipVal,countVal, expiredVal, NotificationType.values(), orderStyle) :
				notificationService.getAllNotificationsForUser(reqUserId, skipVal, countVal,true,vals, orderStyle);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json; charset=UTF-8");
		headers.set("Accept-Charset", "utf-8");
		ResponseEntity<String> response = 
				new ResponseEntity<String>(gson.toJson(list),headers,HttpStatus.OK);
		return response;
	}
	
}

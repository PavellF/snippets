package controllers;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import enums.NotificationType;
import enums.Order;
import enums.OriginType;
import enums.SortBy;
import exceptions.UserDoesNotExistException;
import internationalization.InternationalizationService;
import internationalization.Locale;
import objects.Friend;
import objects.Message;
import objects.User;
import persistence.MessagesRepository.Archived;
import security.SecurityUtilities;
import services.FriendService;
import services.MessagesService;
import services.NotificationService;
import services.UserService;
import util.HTTPUtils;

@Controller
@RequestMapping(value="/users")
public class UserPageController {

	@Autowired 
	private InternationalizationService internationalizationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FriendService friendService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private MessagesService messagesService;
	
	@Autowired
	private Gson gson;
	
	private static final Logger logger = LogManager.getLogger(UserPageController.class.getName());
	
	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String showUserPage(
			@PathVariable String username,
			@CookieValue(value="country", required=false) String countryCookie,
			Model model){
		
		Locale locale = null;
		User user = null;
		User principalUser = SecurityUtilities.getPrincipalUser();
		boolean isPrincipalRequest = false;
		boolean isAuthenticated = false;
		
		if(principalUser != null){
			
			locale = principalUser.getLocale();
			isPrincipalRequest = username.equals(principalUser.getUsername());
			isAuthenticated = true;
			
		}else{
			
			locale = (countryCookie != null) ? 
					(Locale) HTTPUtils.parseEnum(countryCookie, Locale.values()) : Locale.EN;
			
		}
		
		logger.info("User's countryCookie value is "+countryCookie);
		logger.info("Is principal requesting his own user page -> "+isPrincipalRequest);
		
		if(isPrincipalRequest){
			
			user = principalUser;
			
			final String json = gson.toJson(principalUser);
			
			model.addAttribute("principalJSON",json);
			model.addAttribute("userJSON",json);
		}else{
			user = userService.getUserByName(username);
			
			if(null == user) throw new UserDoesNotExistException("Can not find "+username);
			
			model.addAttribute("userJSON", gson.toJson(user));
			
			if(isAuthenticated){
				model.addAttribute("principalJSON", gson.toJson(principalUser));
			}else{
				model.addAttribute("principalJSON", gson.toJson(null));
			}
		}
		
		model.addAttribute("messagesJSON", getMessages(user.getId()));
		model.addAttribute("userFriendsJSON",getFriends(isPrincipalRequest,user));
		model.addAttribute("notificationsJSON", getNotifications(isPrincipalRequest,user));
		model.addAttribute("userProfilePic", user.getUserPicURL());
		model.addAttribute("userID", user.getId());
		model.addAttribute("isAuthenticated", isAuthenticated);
		model.addAttribute("isPrincipalRequest", isPrincipalRequest);
		model.addAttribute("amountOfFriends", friendService.getCount(user.getId()));
		model.addAttribute("amountOfNotifications", notificationService.getCountForUser(user.getId()));
		model.addAttribute("amountOfMessages", messagesService.getCount(OriginType.USER_PROFILE, user.getId(), Archived.IGNORE));

		model.addAllAttributes(internationalizationService.mergeAndGetAllFileContent
				(locale, Aliases.USER_PROFILE_PAGE,Aliases.FOOTER_AND_HEADER, Aliases.MESSAGES_BLOCK));
		
		return Aliases.USER_PROFILE_PAGE;
	}
	
	@RequestMapping(value="/me", method=RequestMethod.GET)
	public String me(RedirectAttributes data){
		
		User principalUser = SecurityUtilities.getPrincipalUser();
		
		if(principalUser == null) return "redirect:/login";
		
		data.addAttribute("username", principalUser.getUsername());
		
		return "redirect:/users/{username}";
	}
	
	
	
	private String getFriends(boolean isPrincipalRequest,User user){
		
		List<Friend> list;
		
		if(isPrincipalRequest){
			list = friendService.getUserFriends(user.getId(), 0, 5, false, SortBy.POPULARITY, Order.DESCENDING);
			
			if(list.size() > 0) return gson.toJson(list);
		}
		
		return gson.toJson(friendService.getUserFriends(user.getId(), 0, 5, true, SortBy.POPULARITY, Order.DESCENDING));
	}
	
	private String getNotifications(boolean isPrincipalRequest,User user){
			return (isPrincipalRequest) ? gson.toJson(notificationService.getAllNotificationsForUser(user.getId(), 0, 5, Order.DESCENDING)) 
			: gson.toJson(this.notificationService.getAllNotificationsForUser(user.getId(),0,5,true,
							new NotificationType[]{NotificationType.REPLY,NotificationType.LIKE,NotificationType.DISLIKE},Order.DESCENDING));
	}
	
	private String getMessages(Long id){
		
		List<Message> list = this.messagesService.getAllMessages(
				OriginType.USER_PROFILE,
				id, 
				Archived.IGNORE,
				SortBy.RATING,
				Order.DESCENDING, 9, 0);
		
		return gson.toJson(list);
	}
	
}

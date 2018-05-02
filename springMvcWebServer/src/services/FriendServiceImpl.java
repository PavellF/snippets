package services;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import controllers.Aliases;
import enums.NotificationType;
import enums.Order;
import enums.SortBy;
import enums.Status;
import internationalization.InternationalizationService;
import internationalization.Locale;
import messagingServices.NotificationFeedService;
import objects.Friend;
import objects.Notification;
import objects.User;
import persistence.FriendsRepository;

@Component
public class FriendServiceImpl implements FriendService {

	private static final Logger logger = Logger.getLogger(FriendServiceImpl.class);
	
	@Autowired
	private FriendsRepository friendsRepository;
	
	@Autowired
	private NotificationFeedService notificationFeedService;
	
	@Autowired 
	private InternationalizationService internationalizationService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Override
	public Status addToFriends(User suggested, final long asker) {
		
		final long suggestedID = suggested.getId();
		
		if(suggestedID == asker){
			logger.error("User cannot add himself in friends.");
			return Status.EXCEPTION;
		}
		
		final long countOfRelation = this.friendsRepository.isRelationExist(suggestedID, asker);
		
		if(countOfRelation != 0){
			logger.error("Users cannot add each other in friends more than one time.");
			return Status.DUPLICATE;
		}
		
		Friend entry = new Friend();
		entry.setUserID(suggestedID);
		entry.setFriendID(asker);
		
		if(!this.friendsRepository.createEntry(entry)) 
			return Status.EXCEPTION;
		
		logger.info("Status SUCCESS. But users are not friends until suggested user will accept friendship.");
		
		Locale locale = suggested.getLocale();
		
		final String content = (String) internationalizationService.getFileEntry(
				Aliases.NOTIFICATION_CONTENT, locale, NotificationType.ASK_FOR_FRIEND.toString(), String.class);
				
		Notification n = notificationService.send(
				asker,
				suggestedID,
				NotificationType.ASK_FOR_FRIEND,
				content);
		
		notificationFeedService.broadcastNotification(n,suggested.getEmail());
		
		return Status.SUCCESS;
	}

	@Override
	public Status deleteFromFriends(long fromUser, long forUser) {
		
		List<Friend> relation = this.friendsRepository.readRelation(forUser,fromUser);
		
		final int size = relation.size();
		
		if(relation == null || size == 0) return Status.EXCEPTION;
		
		switch(size){
		
		case 1: {
			
			if(!this.friendsRepository.deleteEntry(relation.get(0))) return Status.EXCEPTION;
			
		}break;
		
		case 2: {
			
			if(!this.friendsRepository.deleteRelation(relation.get(0), relation.get(1))) return Status.EXCEPTION;
			
		}break;
		
		}
		
		return Status.SUCCESS;
	}

	@Override
	public Status acceptFriend(long suggested, long asker) {
		
		List<Friend> relation = this.friendsRepository.readRelation(suggested, asker);
		
		if(relation == null || relation.size() != 1) return Status.EXCEPTION;
		
		final Date since = new Date();
		
		final Friend friend = relation.get(0);
		friend.setMutual(true);
		friend.setSince(since);
		
		final Friend initiator = new Friend();
		initiator.setMutual(true);
		initiator.setSince(since);
		initiator.setUserID(asker);
		initiator.setFriendID(suggested);
		
		if(!this.friendsRepository.createRelation(friend, initiator)) return Status.EXCEPTION;
		
		return Status.SUCCESS;
	}

	@Override
	public List<Friend> getUserFriends(long userId, int skip, int count, boolean mutual, 
			SortBy sortBy, Order order) throws HibernateException {
		return this.friendsRepository.getUserFriends(userId, skip, count, mutual, sortBy, order);
	}

	@Override
	public Long getCount(long userId) throws HibernateException {
		return this.friendsRepository.getCount(userId);
	}

}

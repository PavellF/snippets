package services;

import java.util.List;

import enums.Order;
import enums.OriginType;
import enums.SortBy;
import enums.Status;
import objects.Message;
import objects.User;
import persistence.MessagesRepository.Archived;

public interface MessagesService {

	public List<Message> getAllMessagesForUser(long userID, Archived archived, SortBy sort, Order order,int count, int skip);
	
	public List<Message> getAllMessages(OriginType originType, long originID, 
			Archived archived, SortBy sort, Order order,int count, int skip);

	public Message createMessage(User author, String content, OriginType originType, long originID, long reply);
	
	public Status changeMessageRating(long id, long value, User by);
	
	public Status changeMessageContent(User author ,long id, String newContent);
	
	public Status archiveMessage(User author ,long id);
	
	public Long getCount(long userID, Archived archived);
	
	public Long getCount(OriginType originType, long originID, Archived archived);
}

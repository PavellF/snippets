package com.serious.business.service;

import java.util.List;

import com.serious.business.domain.ChatDTO;

public interface ChatService {

	List<ChatDTO> getAllChatsForThisUser(int id);
	
	int createChat(ChatDTO chat);
	
	ChatDTO getChatById(int id);
	
	void deleteChatById(int id);
	
	void updateChat(ChatDTO chat);
	
	boolean isThisUserMemberOfChat(int chatId, int userId);
}

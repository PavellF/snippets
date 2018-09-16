package com.serious.business.service;

import java.util.List;

import com.serious.business.domain.ChatMessageDTO;

public interface ChatMessageService {

	List<ChatMessageDTO> getAllChatMessagesInThisChat(int chatId);
	
	Integer saveMessage(ChatMessageDTO message);
	
	void deleteMessage(int messageId);
	
}

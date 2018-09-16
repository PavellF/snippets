package com.serious.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.serious.business.repository.domain.ChatMessage;

public interface ChatMessageRepository extends
	JpaRepository<ChatMessage, Integer> {
	
	@Query("SELECT m FROM ChatMessage AS m WHERE m.chatId = ?1")
	List<ChatMessage> findAllChatMessagesInThisChat(int chatId);

}

package com.serious.business.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.serious.business.repository.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

	@Query("SELECT c FROM Chat AS c WHERE c.openerId = ?1 "
			+ "OR c.chatWithId = ?1")
	List<Chat> findAllChatsForThisUser(int userId);
	
	@Query("SELECT COUNT(c) FROM Chat AS c WHERE (c.openerId = ?2 "
			+ "OR c.chatWithId = ?2) AND c.id = ?1")
	int countChatIdAndUser(int chatId, int userId);
}

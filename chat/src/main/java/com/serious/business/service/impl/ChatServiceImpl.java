package com.serious.business.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.serious.business.domain.ChatDTO;
import com.serious.business.repository.ChatRepository;
import com.serious.business.repository.domain.Chat;
import com.serious.business.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

	private ChatRepository chatRepository;
	private final static Function<Chat, ChatDTO> MAPPER = (Chat c) -> {
		return ChatDTO.builder()
				.withId(c.getId())
				.withChatWithId(c.getChatWithId())
				.withOpenerId(c.getOpenerId())
				.withTitle(c.getTitle())
				.build();
	};
	
	@Autowired
	public ChatServiceImpl(ChatRepository chatRepository) {
		this.chatRepository = chatRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ChatDTO> getAllChatsForThisUser(int id) {
		return chatRepository.findAllChatsForThisUser(id).stream()
				.map(MAPPER).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public int createChat(ChatDTO chat) {
		Assert.notNull(chat, "The given Chat must not be null!");
		
		Chat newChat = new Chat();
		newChat.setChatWithId(chat.getChatWithId());
		newChat.setOpenerId(chat.getOpenerId());
		newChat.setTitle(chat.getTitle());
		
		return chatRepository.save(newChat).getId();
	}

	@Override
	@Transactional(readOnly = true)
	public ChatDTO getChatById(int id) {
		return chatRepository.findById(id).map(MAPPER).orElse(null);
	}

	@Override
	@Transactional
	public void deleteChatById(int id) {
		chatRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void updateChat(ChatDTO chat) {
		Assert.notNull(chat, "The given Chat must not be null!");
		Assert.notNull(chat.getId(), "The given chat id must not be null!");
		
		Chat toUpdate = new Chat();
		toUpdate.setId(chat.getId());
		toUpdate.setTitle(chat.getTitle());
		
		chatRepository.save(toUpdate);
	}

	@Override
	public boolean isThisUserMemberOfChat(int chatId, int userId) {
		return chatRepository.countChatIdAndUser(chatId, userId) == 1;
	}

}

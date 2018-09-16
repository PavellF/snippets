package com.serious.business.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.serious.business.domain.ChatMessageDTO;
import com.serious.business.domain.MessageStatus;
import com.serious.business.repository.ChatMessageRepository;
import com.serious.business.repository.domain.ChatMessage;
import com.serious.business.service.ChatMessageService;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

	private ChatMessageRepository cmr;
	
	@Autowired
	public ChatMessageServiceImpl(ChatMessageRepository cmr) {
		this.cmr = cmr;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ChatMessageDTO> getAllChatMessagesInThisChat(int chatId) {
		return cmr.findAllChatMessagesInThisChat(chatId)
				.stream().map(m -> ChatMessageDTO.builder()
					.withAuthorId(m.getAuthorId())
					.withChatId(m.getChatId())
					.withDate(m.getDate())
					.withId(m.getId())
					.withMessage(m.getMessage())
					.withRole(m.getRole()).build()
		).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Integer saveMessage(ChatMessageDTO message) {
		Assert.notNull(message, "The given ChatMessageDTO must not be null!");
		
		ChatMessage cm = new ChatMessage();
		cm.setAuthorId(message.getAuthorId());
		cm.setChatId(message.getChatId());
		cm.setDate(Instant.now());
		cm.setMessage(message.getMessage());
		cm.setRole(MessageStatus.UNREAD);
		
		return cmr.save(cm).getId();
	}

	@Override
	public void deleteMessage(int messageId) {
		cmr.deleteById(messageId);
	}

}

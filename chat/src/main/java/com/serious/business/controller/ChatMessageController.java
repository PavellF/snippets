package com.serious.business.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serious.business.domain.ChatMessageDTO;
import com.serious.business.security.SecurityContext;
import com.serious.business.service.ChatMessageService;
import com.serious.business.service.ChatService;

@RestController
public class ChatMessageController {

	private ChatMessageService cms;
	private ChatService chatService;
	private SecurityContext securityContext;
	
	@Autowired
	public ChatMessageController(ChatMessageService chatMessage,
			ChatService chatService, SecurityContext securityContext) {
		this.cms = chatMessage;
		this.chatService = chatService;
		this.securityContext = securityContext;
	}
	
	private boolean isInChat(Integer chatId) {
		if (chatId == null) {
			return false;
		}
		
		return securityContext.getId()
				.map(Integer::valueOf)
				.map(id -> chatService.isThisUserMemberOfChat(chatId, id))
				.orElse(false);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, 
			path = "/chat/{id}/message")	
	public ResponseEntity<List<ChatMessageDTO>> getAllChatMessagesInThisChat(
			@PathVariable("id") int chatId) {
		
		return isInChat(chatId) 
				? ResponseEntity.ok(cms.getAllChatMessagesInThisChat(chatId)) 
				: ResponseEntity.badRequest().build();
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, path = "/message")	
	public ResponseEntity<?> postMessage(ChatMessageDTO message) {
		
		if (message == null) {
			return ResponseEntity.badRequest().build();
		}
		
		Integer chatId = message.getChatId();
		
		boolean isLegal = securityContext.getId()
				.map(Integer::valueOf)
				.map(id -> chatService.isThisUserMemberOfChat(chatId, id)
						&& id == message.getAuthorId())
				.orElse(false);
		
		if (isLegal) {
			URI location = URI.create("/chat/" + chatId + "/message");
			cms.saveMessage(message);
			return ResponseEntity.created(location).build();
		} 
		
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping(path = "/message/{id}")	
	public ResponseEntity<?> deleteMessage(@PathVariable("id") int messageID) {
		
		if (this.securityContext.isSuper()) {
			cms.deleteMessage(messageID);
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
}

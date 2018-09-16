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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serious.business.domain.ChatDTO;
import com.serious.business.security.SecurityContext;
import com.serious.business.service.ChatService;

@RestController
public class ChatController {

	private ChatService chatService;
	private SecurityContext securityContext;
	
	@Autowired
	public ChatController(ChatService chatService,
			SecurityContext securityContext) {
		this.chatService = chatService;
		this.securityContext = securityContext;
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, 
			path = "/user/{id}/chat")	
	public ResponseEntity<List<ChatDTO>> getAllChatsForThisUser(
			@PathVariable("id") String id) {
		
		Boolean selfMadeRequest = securityContext.getId()
				.map(userId -> userId.equals(id))
				.orElse(Boolean.FALSE);
			
		if (securityContext.isSuper() || selfMadeRequest) {
			Integer uid = Integer.valueOf(id);
			return ResponseEntity.ok(chatService.getAllChatsForThisUser(uid));
		}
			
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/chat")	
	public ResponseEntity<?> openChat(ChatDTO chat) {
		
		if (chat == null) {
			return ResponseEntity.badRequest().build();
		}
		
		boolean isLegal = securityContext.getId().map(Integer::valueOf)
			.map(openerId -> chat.getOpenerId() == openerId && 
					chat.getOpenerId() != chat.getChatWithId())
			.orElse(false);
		
		if (isLegal || securityContext.isSuper()) {
			URI location = URI.create("/user/" + chat.getOpenerId() + "/chat");
			chatService.createChat(chat);
			return ResponseEntity.created(location).build();
		} 
		
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/chat")	
	public ResponseEntity<?> updateChat(ChatDTO chat) {
		
		if (chat == null) {
			return ResponseEntity.badRequest().build();
		}
		
		final Integer chatID = chat.getId();
		
		if (chatID == null) {
			return ResponseEntity.notFound().build();
		}
		
		final boolean isMember = securityContext.getId()
				.map(Integer::valueOf)
				.map(id -> chatService.isThisUserMemberOfChat(chatID, id))
				.orElse(false);
		
		if (isMember) {
			this.chatService.updateChat(chat);
			return ResponseEntity.noContent().build();
		}
		
		//current user has nothing in common with this chat
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping(path = "/chat/{id}")	
	public ResponseEntity<?> deleteChat(@PathVariable("id") int chatID) {
		
		if (this.securityContext.isSuper()) {
			chatService.deleteChatById(chatID);
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}

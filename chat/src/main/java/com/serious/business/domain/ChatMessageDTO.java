package com.serious.business.domain;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ChatMessageDTO {

	private Integer id;
	private Integer authorId;
	private Integer chatId;
	private Instant date;
	private String message;
	private MessageStatus role;

	private ChatMessageDTO(Builder builder) {
		this.id = builder.id;
		this.authorId = builder.authorId;
		this.chatId = builder.chatId;
		this.date = builder.date;
		this.message = builder.message;
		this.role = builder.role;
	}
	
	private ChatMessageDTO() {}

	public Integer getId() {
		return id;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public Integer getChatId() {
		return chatId;
	}

	public Instant getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}

	public MessageStatus getRole() {
		return role;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ChatMessageDTO [id=");
		builder.append(id);
		builder.append(", authorId=");
		builder.append(authorId);
		builder.append(", chatId=");
		builder.append(chatId);
		builder.append(", date=");
		builder.append(date);
		builder.append(", message=");
		builder.append(message);
		builder.append(", role=");
		builder.append(role);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Creates builder to build {@link ChatMessageDTO}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link ChatMessageDTO}.
	 */
	public static final class Builder {
		private Integer id;
		private Integer authorId;
		private Integer chatId;
		private Instant date;
		private String message;
		private MessageStatus role;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withAuthorId(Integer authorId) {
			this.authorId = authorId;
			return this;
		}

		public Builder withChatId(Integer chatId) {
			this.chatId = chatId;
			return this;
		}

		public Builder withDate(Instant date) {
			this.date = date;
			return this;
		}

		public Builder withMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder withRole(MessageStatus role) {
			this.role = role;
			return this;
		}

		public ChatMessageDTO build() {
			return new ChatMessageDTO(this);
		}
	}
	
	
	
}

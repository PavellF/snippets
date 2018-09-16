package com.serious.business.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ChatDTO {

	private Integer id;
	private String title;
	private Integer openerId;
	private Integer chatWithId;

	private ChatDTO(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.openerId = builder.openerId;
		this.chatWithId = builder.chatWithId;
	}
	
	private ChatDTO() {}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Integer getOpenerId() {
		return openerId;
	}

	public Integer getChatWithId() {
		return chatWithId;
	}

	/**
	 * Creates builder to build {@link ChatDTO}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link ChatDTO}.
	 */
	public static final class Builder {
		private Integer id;
		private String title;
		private Integer openerId;
		private Integer chatWithId;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder withOpenerId(Integer openerId) {
			this.openerId = openerId;
			return this;
		}

		public Builder withChatWithId(Integer chatWithId) {
			this.chatWithId = chatWithId;
			return this;
		}

		public ChatDTO build() {
			return new ChatDTO(this);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("ChatDTO [id=");
		builder2.append(id);
		builder2.append(", title=");
		builder2.append(title);
		builder2.append(", openerId=");
		builder2.append(openerId);
		builder2.append(", chatWithId=");
		builder2.append(chatWithId);
		builder2.append("]");
		return builder2.toString();
	}
	
}

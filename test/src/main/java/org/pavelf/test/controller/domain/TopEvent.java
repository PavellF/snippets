package org.pavelf.test.controller.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents event id and count of times it has happen.
 * @author Pavel F.
 * @since 1.0
 */
public class TopEvent {

	@JsonProperty("formi")
	private String id;
	private Long count;

	private TopEvent(Builder builder) {
		this.id = builder.id;
		this.count = builder.count;
	}

	private TopEvent() {}

	public String getId() {
		return id;
	}

	public Long getCount() {
		return count;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TopEvent [id=");
		builder.append(id);
		builder.append(", count=");
		builder.append(count);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Creates builder to build {@link TopEvent}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link TopEvent}.
	 */
	public static final class Builder {
		private String id;
		private Long count;

		private Builder() {
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withCount(Long count) {
			this.count = count;
			return this;
		}

		public TopEvent build() {
			return new TopEvent(this);
		}
	}
	
	
}

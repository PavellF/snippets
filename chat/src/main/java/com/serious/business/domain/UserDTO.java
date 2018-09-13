package com.serious.business.domain;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserDTO {

	private Integer id;
	
	private String email;
	
	private char[] password;
	
	private Role role;
	
	private Instant date;

	private UserDTO(Builder builder) {
		this.id = builder.id;
		this.email = builder.email;
		this.password = builder.password;
		this.role = builder.role;
		this.date = builder.date;
	}

	private UserDTO() { }

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public char[] getPassword() {
		return password;
	}

	public Role getRole() {
		return role;
	}

	public Instant getDate() {
		return date;
	}

	/**
	 * Creates builder to build {@link UserDTO}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link UserDTO}.
	 */
	public static final class Builder {
		private Integer id;
		private String email;
		private char[] password;
		private Role role;
		private Instant date;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder withPassword(char[] password) {
			this.password = password;
			return this;
		}

		public Builder withRole(Role role) {
			this.role = role;
			return this;
		}

		public Builder withDate(Instant date) {
			this.date = date;
			return this;
		}

		public UserDTO build() {
			return new UserDTO(this);
		}
	}

	
}

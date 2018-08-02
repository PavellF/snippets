	package org.pavelf.test.controller.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data transaction object for exposing to outside world. Usually serializes 
 * into JSON or XML and sends on network. Immutable.
 * @author Pavel F.
 * @since 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDTO {

	@JsonProperty("ssoid")
	private	String id;
	
	@JsonProperty("ts")
	private Long time;
	
	@JsonProperty("grp")
	private String group;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("subtype")
	private String subtype;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("orgid")
	private String organisationId;
	
	@JsonProperty("formid")
	private String formId;
	
	@JsonProperty("ltpa")
	private String sessionKey;
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("sudirresponse")
	private String authResponse;
	
	@JsonProperty("ymdh")
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-HH")
	private Date ymdh;

	private EventDTO(Builder builder) {
		this.id = builder.id;
		this.time = builder.time;
		this.group = builder.group;
		this.type = builder.type;
		this.subtype = builder.subtype;
		this.url = builder.url;
		this.organisationId = builder.organisationId;
		this.formId = builder.formId;
		this.sessionKey = builder.sessionKey;
		this.code = builder.code;
		this.authResponse = builder.authResponse;
		this.ymdh = builder.ymdh;
	}

	private EventDTO() {}

	public String getId() {
		return id;
	}

	public Long getTime() {
		return time;
	}

	public String getGroup() {
		return group;
	}

	public String getType() {
		return type;
	}

	public String getSubtype() {
		return subtype;
	}

	public String getUrl() {
		return url;
	}

	public String getOrganisationId() {
		return organisationId;
	}

	public String getFormId() {
		return formId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public String getCode() {
		return code;
	}

	public String getAuthResponse() {
		return authResponse;
	}

	public Date getYmdh() {
		return ymdh;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EventDTO [id=");
		builder.append(id);
		builder.append(", time=");
		builder.append(time);
		builder.append(", group=");
		builder.append(group);
		builder.append(", type=");
		builder.append(type);
		builder.append(", subtype=");
		builder.append(subtype);
		builder.append(", url=");
		builder.append(url);
		builder.append(", organisationId=");
		builder.append(organisationId);
		builder.append(", formId=");
		builder.append(formId);
		builder.append(", sessionKey=");
		builder.append(sessionKey);
		builder.append(", code=");
		builder.append(code);
		builder.append(", authResponse=");
		builder.append(authResponse);
		builder.append(", ymdh=");
		builder.append(ymdh);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Creates builder to build {@link EventDTO}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link EventDTO}.
	 */
	public static final class Builder {
		private String id;
		private Long time;
		private String group;
		private String type;
		private String subtype;
		private String url;
		private String organisationId;
		private String formId;
		private String sessionKey;
		private String code;
		private String authResponse;
		private Date ymdh;

		private Builder() {
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withTime(Long time) {
			this.time = time;
			return this;
		}

		public Builder withGroup(String group) {
			this.group = group;
			return this;
		}

		public Builder withType(String type) {
			this.type = type;
			return this;
		}

		public Builder withSubtype(String subtype) {
			this.subtype = subtype;
			return this;
		}

		public Builder withUrl(String url) {
			this.url = url;
			return this;
		}

		public Builder withOrganisationId(String organisationId) {
			this.organisationId = organisationId;
			return this;
		}

		public Builder withFormId(String formId) {
			this.formId = formId;
			return this;
		}

		public Builder withSessionKey(String sessionKey) {
			this.sessionKey = sessionKey;
			return this;
		}

		public Builder withCode(String code) {
			this.code = code;
			return this;
		}

		public Builder withAuthResponse(String authResponse) {
			this.authResponse = authResponse;
			return this;
		}

		public Builder withYmdh(Date ymdh) {
			this.ymdh = ymdh;
			return this;
		}

		public EventDTO build() {
			return new EventDTO(this);
		}
	}

	
	
}
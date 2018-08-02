package org.pavelf.test.domain;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ORM {@code @Entity} that represents event.
 * @author Pavel F.
 * @since 1.0
 * */
@Entity
@Table(name="events")
public class Event {
	
	@Id
	@Column(name="ssoid")
	private UUID id;
	
	@Column(name="ts")
	@NotNull
	private Instant time;
	
	@Column(name="grp")
	@NotNull
	@Size(min = 0, max = 64)
	private String group;
	
	@Column(name="type")	
	@NotNull
	@Size(min = 0, max = 64)
	private String type;
	
	@Column(name="subtype")
	@NotNull
	@Size(min = 0, max = 64)
	private String subtype;
	
	@Column(name="code")
	@NotNull
	@Size(min = 0, max = 64)
	private String code;
	
	@Column(name="url")
	@NotNull
	@Size(min = 0, max = 1024)
	private String url;
	
	@Column(name="orgid")
	@NotNull
	@Size(min = 0, max = 64)
	private String organisationId;
	
	@Column(name="formid")
	@NotNull
	@Size(min = 0, max = 64)
	private String formId;
	
	@Column(name="ltpa")
	@Size(min = 0, max = 64)
	private String sessionKey;
	
	@Column(name="sudirresponse")
	@Size(min = 0, max = 64)
	private String authResponse;
	
	@Column(name="ymdh")
	@NotNull
	private Instant ymdh;

	/**
	 * Does NOT trigger lazy loading. Always returns empty string.
	 * */
	@Override
	public String toString() {
		return "";
	}

	/**
	 * Does NOT trigger lazy loading. Works similar to default implementation.
	 * */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * Does NOT trigger lazy loading. Always throws.
	 * @throws UnsupportedOperationException
	 * */
	@Override
	public boolean equals(Object obj) {
		throw new UnsupportedOperationException();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(String organisationId) {
		this.organisationId = organisationId;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getAuthResponse() {
		return authResponse;
	}

	public void setAuthResponse(String authResponse) {
		this.authResponse = authResponse;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Instant getYmdh() {
		return ymdh;
	}

	public void setYmdh(Instant ymdh) {
		this.ymdh = ymdh;
	}

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	
}

package objects;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import enums.NotificationType;

/**
CREATE TABLE notifications(
id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
type VARCHAR(128) NOT NULL,
date DATETIME NOT NULL DEFAULT NOW(),
by_user BIGINT UNSIGNED NOT NULL,
for_user BIGINT UNSIGNED NOT NULL,
content VARCHAR(256) NOT NULL,
expired BIT(1) DEFAULT 0,
FOREIGN KEY (by_user) REFERENCES users(id)
)  ENGINE=InnoDB  DEFAULT CHARSET=utf8;
**/

@Entity
@Table(name="notifications")
@SuppressWarnings("serial")
public class Notification implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	@Expose
	private long id;
	
	@Expose
	@Column(name="type")
	@Enumerated(value = EnumType.STRING)
	private NotificationType type;
	
	@Expose
	@Column(name="date")
	private Date date;
	
	@Expose
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="by_user", insertable=false, updatable=false)
	@SerializedName("by")
	private User byUser;
	
	@Column(name="for_user")
	private long forUser;
	
	@Column(name="expired")
	private boolean expired;
	
	@Column(name="by_user")
	private long byUserID;
	
	@Column(name="content")
	@Expose
	private String content;

	public long getId() {
		return id;
	}

	public void setId(long id){
		this.id = id;
	}
	
	public NotificationType getType() {
		return type;
	}

	public void setType(NotificationType type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getByUser() {
		return byUser;
	}

	public long getForUser() {
		return forUser;
	}

	public void setForUser(long forUser) {
		this.forUser = forUser;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public long getByUserID() {
		return byUserID;
	}

	public void setByUserID(long byUserID) {
		this.byUserID = byUserID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((byUser == null) ? 0 : byUser.hashCode());
		result = prime * result + (int) (byUserID ^ (byUserID >>> 32));
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (expired ? 1231 : 1237);
		result = prime * result + (int) (forUser ^ (forUser >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		if (byUser == null) {
			if (other.byUser != null)
				return false;
		} else if (!byUser.equals(other.byUser))
			return false;
		if (byUserID != other.byUserID)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (expired != other.expired)
			return false;
		if (forUser != other.forUser)
			return false;
		if (id != other.id)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Notification [id=");
		builder.append(id);
		builder.append(", type=");
		builder.append(type);
		builder.append(", date=");
		builder.append(date);
		builder.append(", byUser=");
		builder.append(byUser);
		builder.append(", forUser=");
		builder.append(forUser);
		builder.append(", expired=");
		builder.append(expired);
		builder.append(", byUserID=");
		builder.append(byUserID);
		builder.append(", content=");
		builder.append(content);
		builder.append("]");
		return builder.toString();
	}
	
}

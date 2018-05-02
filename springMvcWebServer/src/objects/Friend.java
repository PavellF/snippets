package objects;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
CREATE TABLE friends(
id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
user_id BIGINT UNSIGNED NOT NULL,
friend_id BIGINT UNSIGNED NOT NULL,
since DATETIME DEFAULT NULL,
mutual BIT(1) DEFAULT 0,
FOREIGN KEY (friend_id) REFERENCES users(id),
)  ENGINE=InnoDB  DEFAULT CHARSET=utf8;
 * */

@SuppressWarnings("serial")
@Entity
@Table(name="friends")
public class Friend implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name = "user_id")
	private long userID;
	
	@Column(name = "friend_id")
	private long friendID;
	
	@Expose
	@SerializedName("friend")
	@JoinColumn(name = "friend_id", insertable=false, updatable=false)
	@ManyToOne(fetch = FetchType.EAGER)
	private User userFriend;
	
	@Expose
	@Column(name="since")
	private Date since;
	
	@Column(name = "mutual")
	private boolean mutual;

	public long getId() {
		return id;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public long getFriendID() {
		return friendID;
	}

	public void setFriendID(long friendID) {
		this.friendID = friendID;
	}

	public User getUserFriend() {
		return userFriend;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public boolean isMutual() {
		return mutual;
	}

	public void setMutual(boolean mutual) {
		this.mutual = mutual;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (friendID ^ (friendID >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (mutual ? 1231 : 1237);
		result = prime * result + ((since == null) ? 0 : since.hashCode());
		result = prime * result + ((userFriend == null) ? 0 : userFriend.hashCode());
		result = prime * result + (int) (userID ^ (userID >>> 32));
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
		Friend other = (Friend) obj;
		if (friendID != other.friendID)
			return false;
		if (id != other.id)
			return false;
		if (mutual != other.mutual)
			return false;
		if (since == null) {
			if (other.since != null)
				return false;
		} else if (!since.equals(other.since))
			return false;
		if (userFriend == null) {
			if (other.userFriend != null)
				return false;
		} else if (!userFriend.equals(other.userFriend))
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Friend [id=");
		builder.append(id);
		builder.append(", userID=");
		builder.append(userID);
		builder.append(", friendID=");
		builder.append(friendID);
		builder.append(", userFriend=");
		builder.append(userFriend);
		builder.append(", since=");
		builder.append(since);
		builder.append(", mutual=");
		builder.append(mutual);
		builder.append("]");
		return builder.toString();
	}
	

}

package objects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import enums.OriginType;

/**
 * 
 * CREATE TABLE messages(
 * id BIGINT UNSIGNED NOT NULL  AUTO_INCREMENT PRIMARY KEY,
 * author BIGINT UNSIGNED NOT NULL ,
 * date DATETIME DEFAULT NOW(),
 * origin_type VARCHAR(256) NOT NULL,
 * origin_id BIGINT UNSIGNED NOT NULL,
 * reply BIGINT UNSIGNED DEFAULT NULL,
 * content VARCHAR(MAX) DEFAULT NULL,
 * rating BIGINT DEFAULT 0,
 * changed DATETIME DEFAULT NULL,
 * archived BIT(1) DEFAULT 0,
 * FOREIGN KEY (author) REFERENCES users(id),
 * FOREIGN KEY (reply) REFERENCES messages(id)
 * ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
 */


@SuppressWarnings("serial")
@Entity
@Table(name="messages")
public class Message implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	@Expose
	private long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="author")
	@Expose
	private User author;
	
	@Expose
	@Column(name="date")
	private Date date;
	
	@Enumerated(value=EnumType.STRING)
	@Column(name="origin_type")
	private OriginType originType;
	
	@Column(name="origin_id")
	private long originId;
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	@Expose
	private List<Message> replies;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="reply", insertable=false, updatable=false)
	private Message parent;
	
	@Column(name="reply")
	private Long replied;
	
	@Expose
	@Column(name="content")
	private String content;
	
	@Expose
	@Column(name="rating")
	private long rating;
	
	@Expose
	@Column(name="changed")
	private Date changed;
	
	@Column(name="archived")
	@Expose
	private boolean archived;

	public Message(){
		System.err.println("-0-0-0-0-0-0-0-0-intantiated");
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public OriginType getOriginType() {
		return originType;
	}

	public void setOriginType(OriginType originType) {
		this.originType = originType;
	}

	public long getOriginId() {
		return originId;
	}

	public void setOriginId(long originId) {
		this.originId = originId;
	}

	public List<Message> getReplies() {
		return replies;
	}

	public void setReplies(List<Message> replies) {
		this.replies = replies;
	}

	public Message getParent() {
		return parent;
	}

	public void setParent(Message parent) {
		this.parent = parent;
	}

	public Long getReplied() {
		return replied;
	}

	public void setReplied(Long replied) {
		this.replied = replied;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(long rating) {
		this.rating = rating;
	}

	public Date getChanged() {
		return changed;
	}

	public void setChanged(Date changed) {
		this.changed = changed;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (archived ? 1231 : 1237);
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((changed == null) ? 0 : changed.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (originId ^ (originId >>> 32));
		result = prime * result + ((originType == null) ? 0 : originType.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + (int) (rating ^ (rating >>> 32));
		result = prime * result + ((replied == null) ? 0 : replied.hashCode());
		result = prime * result + ((replies == null) ? 0 : replies.hashCode());
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
		Message other = (Message) obj;
		if (archived != other.archived)
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (changed == null) {
			if (other.changed != null)
				return false;
		} else if (!changed.equals(other.changed))
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
		if (id != other.id)
			return false;
		if (originId != other.originId)
			return false;
		if (originType != other.originType)
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (rating != other.rating)
			return false;
		if (replied == null) {
			if (other.replied != null)
				return false;
		} else if (!replied.equals(other.replied))
			return false;
		if (replies == null) {
			if (other.replies != null)
				return false;
		} else if (!replies.equals(other.replies))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [id=");
		builder.append(id);
		builder.append(", author=");
		builder.append(author);
		builder.append(", date=");
		builder.append(date);
		builder.append(", originType=");
		builder.append(originType);
		builder.append(", originId=");
		builder.append(originId);
		builder.append(", replies=");
		builder.append(replies);
		builder.append(", parent=");
		builder.append(parent);
		builder.append(", replied=");
		builder.append(replied);
		builder.append(", content=");
		builder.append(content);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", changed=");
		builder.append(changed);
		builder.append(", archived=");
		builder.append(archived);
		builder.append("]");
		return builder.toString();
	}


}

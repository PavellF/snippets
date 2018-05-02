package objects;

import com.google.gson.annotations.Expose;

import enums.OriginType;

public class MessageForm{

	@Expose
	private OriginType origin_type;
	
	@Expose
	private long origin_id;
	
	@Expose
	private String content;
	
	@Expose
	private long reply;

	public OriginType getOriginType() {
		return origin_type;
	}

	public void setOriginType(OriginType origin_type) {
		this.origin_type = origin_type;
	}

	public long getOriginId() {
		return origin_id;
	}

	public void setOriginId(long origin_id) {
		this.origin_id = origin_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getReply() {
		return reply;
	}

	public void setReply(long reply) {
		this.reply = reply;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + (int) (origin_id ^ (origin_id >>> 32));
		result = prime * result + ((origin_type == null) ? 0 : origin_type.hashCode());
		result = prime * result + (int) (reply ^ (reply >>> 32));
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
		MessageForm other = (MessageForm) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (origin_id != other.origin_id)
			return false;
		if (origin_type != other.origin_type)
			return false;
		if (reply != other.reply)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageForm [origin_type=");
		builder.append(origin_type);
		builder.append(", origin_id=");
		builder.append(origin_id);
		builder.append(", content=");
		builder.append(content);
		builder.append(", reply=");
		builder.append(reply);
		builder.append("]");
		return builder.toString();
	}

	
}

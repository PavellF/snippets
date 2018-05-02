package objects;

import com.google.gson.annotations.Expose;

public class UpdateMessageForm{

	@Expose
	private long messageID;
	
	@Expose
	private String newContent;

	public long getMessageID() {
		return messageID;
	}

	public String getNewContent() {
		return newContent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AskForUpdateMessage [messageID=");
		builder.append(messageID);
		builder.append(", newContent=");
		builder.append(newContent);
		builder.append("]");
		return builder.toString();
	}
}

package objects;

import java.io.Serializable;

@SuppressWarnings("serial")
public class IterationPOJO implements Serializable{

	private int id;
	private String access_token;
	private String group_id;
	private String user_id;
	private long delay;
	private int countOfPosts;
	private long globalDelay;
	private PhotoSize photoSize;
	private boolean filter;
	private String savedPhotoDestination;
	private String[] groupsFrom;
	private boolean active;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}
	public int getCountOfPosts() {
		return countOfPosts;
	}
	public void setCountOfPosts(int countOfPosts) {
		this.countOfPosts = countOfPosts;
	}
	public long getGlobalDelay() {
		return globalDelay;
	}
	public void setGlobalDelay(long globalDelay) {
		this.globalDelay = globalDelay;
	}
	public PhotoSize getPhotoSize() {
		return photoSize;
	}
	public void setPhotoSize(PhotoSize photoSize) {
		this.photoSize = photoSize;
	}
	public boolean isFilter() {
		return filter;
	}
	public void setFilter(boolean filter) {
		this.filter = filter;
	}
	public String getSavedPhotoDestination() {
		return savedPhotoDestination;
	}
	public void setSavedPhotoDestination(String savedPhotoDestination) {
		this.savedPhotoDestination = savedPhotoDestination;
	}
	public String[] getGroupsFrom() {
		return groupsFrom;
	}
	public void setGroupsFrom(String[] groupsFrom) {
		this.groupsFrom = groupsFrom;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	
}

package services;

import java.net.MalformedURLException;

import objects.PhotoSize;

public interface RepostService {

	public void doRepost (
			String access_token,
			String group_id,
			String user_id,
			PhotoSize photoSize,
			Long delayInMills,
			Integer countOfPosts,
			String... groups) throws MalformedURLException ;
	
	public void doRepostAndSavePhotoOnHardDisk (
			String destination,
			String access_token,
			String group_id,
			String user_id,
			PhotoSize photoSize,
			Long delayInMills,
			Integer countOfPosts,
			String... groups) throws MalformedURLException ;
	
	public void doRepostWithoutContentFilter (
			String access_token,
			String group_id,
			String user_id,
			PhotoSize photoSize,
			Long delayInMills,
			Integer countOfPosts,
			String... groups) throws MalformedURLException ;
	
	public void doRepostAndSavePhotoOnHardDiskWithoutContentFilter (
			String destination,
			String access_token,
			String group_id,
			String user_id,
			PhotoSize photoSize,
			Long delayInMills,
			Integer countOfPosts,
			String... groups) throws MalformedURLException ;
}

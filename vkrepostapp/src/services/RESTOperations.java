package services;

import java.net.MalformedURLException;

import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.objects.wall.responses.GetResponse;

public interface RESTOperations {

	public GetResponse getWallGroup(String owner_id,Integer count);
	public String postPhotoToWall(String access_token,String owner_id,String media_id,String owner_photo_id);
	public PhotoUpload getWallUploadServer(String group_id, String access_token);
	public WallUploadResponse postPhoto(String upload_url,String resource_url) throws MalformedURLException;
	public Photo[] saveWallPhoto(String user_id,String group_id,String access_token,WallUploadResponse wallUploadResponse);
	
}

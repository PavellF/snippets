package services;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.objects.wall.responses.GetResponse;

import responses.GetResponseResponse;
import responses.PhotoResponse;
import responses.PhotoUploadResponse;

public class RESTOperationsImpl implements RESTOperations{

	private final Gson gson = new Gson();
	
	public GetResponse getWallGroup(String owner_id,Integer count){
		RestTemplate template = new RestTemplate();
		StringBuilder url = new StringBuilder();
		url.append("https://api.vk.com/method/wall.get?owner_id=");
		url.append(owner_id);
		url.append("&extended=1&count=");
		url.append(count);
		url.append("&filter=owner&v=5.53");
		ResponseEntity<String> response = template.exchange(
				url.toString(),
				HttpMethod.GET,
				null,
				String.class);
		
		return gson.fromJson(response.getBody(),GetResponseResponse.class).getResponse();
	}
	
	public String postPhotoToWall(String access_token,String owner_id,String media_id,String owner_photo_id){
		StringBuilder url = new StringBuilder();
		url.append("https://api.vk.com/method/wall.post?owner_id=");
		url.append(owner_id);
		url.append("&from_group=1&signed=0&attachments=photo");
		url.append(owner_photo_id);
		url.append("_");
		url.append(media_id);
		url.append("&access_token=");
		url.append(access_token);
		url.append("&v=5.53");
		
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.exchange(
				url.toString(),
				HttpMethod.POST,
				null,
				String.class);
		return response.getBody();
	}
	
	public PhotoUpload getWallUploadServer(String group_id, String access_token){
		StringBuilder url = new StringBuilder();
		url.append("https://api.vk.com/method/photos.getWallUploadServer?group_id=");
		url.append(group_id);
		url.append("&access_token=");
		url.append(access_token);
		url.append("&v=5.53");
		
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.exchange(
				url.toString(),
				HttpMethod.GET,
				null,
				String.class);
		return gson.fromJson(response.getBody(), PhotoUploadResponse.class).getResponse();
	}
	
	public WallUploadResponse postPhoto(String upload_url,String resource_url) throws MalformedURLException{
		
		Resource resource = new UrlResource(resource_url);
		
		MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
		parts.add("photo", resource);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		 
		 HttpEntity<MultiValueMap<String, Object>> requestEntity =
		          new HttpEntity<MultiValueMap<String, Object>>(parts,headers);
		 
		 RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.exchange(
					upload_url,             
					HttpMethod.POST,
					requestEntity,
					String.class);
		return gson.fromJson(response.getBody(), WallUploadResponse.class);
	}
	
	public Photo[] saveWallPhoto(String user_id,String group_id,String access_token,WallUploadResponse wallUploadResponse){
		StringBuilder url = new StringBuilder();
		url.append("https://api.vk.com/method/photos.saveWallPhoto?group_id=");
		url.append(group_id);
		url.append("&user_id=");
		url.append(user_id);
		url.append("&photo={photo}");
		url.append("&server=");
		url.append(wallUploadResponse.getServer());
		url.append("&hash=");
		url.append(wallUploadResponse.getHash());
		url.append("&access_token=");
		url.append(access_token);
		url.append("&v=5.53");
		
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> response = rest.exchange(
				url.toString(),
				HttpMethod.POST,
				null,
				String.class,
				wallUploadResponse.getPhoto());
		return gson.fromJson(response.getBody(), PhotoResponse.class).getResponse();
	}
	
	
}

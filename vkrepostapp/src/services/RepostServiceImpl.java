package services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import com.vk.api.sdk.objects.wall.responses.GetResponse;

import exceptions.DuplicateUrlException;
import objects.PhotoSize;
import websocket.GetLogger;
import websocket.LoggerWebSocketServlet;

public class RepostServiceImpl implements RepostService {

	static final Logger logger = LogManager.getLogger(RepostServiceImpl.class.getName());
	private RESTOperations RESTOperations;
	private final SerializableLoader<List<String>> photoURLsLoader = new SerializableLoader<List<String>>(getPath());
	private LoggerWebSocketServlet log = GetLogger.getwsLogger();
	private final String _nullMessage = 
			"Не удалось получить данные от vk.com. Возможно стоит проверить правильность введённых данных(в особенности id групп).";
	private final String _nullPhoto = "Не удалось найти фото или фото этого размера не существует.";
	
	public RepostServiceImpl(RESTOperations RESTOperations){
		this.RESTOperations = RESTOperations;
	}

	@Override
	public void doRepost(String access_token, String group_id, String user_id, PhotoSize photoSize, Long delayInMills,
			Integer countOfPosts, String... groups) throws MalformedURLException {
		String uploadURL = RESTOperations.getWallUploadServer(group_id,access_token).getUploadUrl();
		StringBuilder groupid = new StringBuilder();
		groupid.append("-");
		groupid.append(group_id);
		int success = 0;
		int filtered = 0;
		
		for(int g = 0;g<groups.length;g++){
			GetResponse response = RESTOperations.getWallGroup(groups[g], countOfPosts+1);
			if(response == null)
			{
				log.send(_nullMessage);
				break;
			}
		success = 0;
		filtered = 0;
		
		for(int i = 1; i < countOfPosts+1; i++){
			String photoURL = new String();
			if(!this.isOnlyPhotoAttachment(response,i)){
				filtered++;
				continue;
			}
			
			//define photo url depending from resolution
			try{
			switch(photoSize){
			case PHOTO_1280:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto1280());
			break;
			case PHOTO_807:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto807());
			break;
			case PHOTO_604:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto604());
			break;
			case PHOTO_130:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto130());
			break;
			case PHOTO_75:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto75());
			break;
			}
			}catch(DuplicateUrlException de){
				logger.error(de.getMessage());
				log.send(de.getMessage());
				filtered++;
				continue;
			}catch(NullPointerException npe){
				log.send(_nullPhoto);
				logger.error(_nullPhoto);
				continue;
			}
			
			Photo[] posted = RESTOperations.saveWallPhoto(
					user_id,
					group_id, 
					access_token, 
					RESTOperations.postPhoto(
							uploadURL,
							photoURL));
			
			RESTOperations.postPhotoToWall(
					access_token,
					groupid.toString(),
					Integer.toString(posted[0].getId()),
					Integer.toString(posted[0].getOwnerId()));
			success++;
			  try {
				Thread.sleep(delayInMills); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		StringBuilder summaryInfo = new StringBuilder();
		summaryInfo.append("ВСЕГО: успешно: ");
		summaryInfo.append(success);
		summaryInfo.append(" отфильтровано: ");
		summaryInfo.append(filtered);
		String string = summaryInfo.toString();
		logger.info(string);
		log.send(string);
		}
		
	}
	
	private String assertNoEqualsAndAdd(String photoURL){
		List<String> photoURLs;
		
		if(photoURL == null || photoURL.isEmpty())
			throw new NullPointerException();
		
		if(Files.exists(Paths.get(getPath()))){
			photoURLs = photoURLsLoader.loadFile();
		}else{
			photoURLs = new ArrayList<String>();
		}
		
		for(int i = 0; i<photoURLs.size();i++){
			if(photoURLs.get(i).equals(photoURL)){
				throw new DuplicateUrlException("Такое фото уже было.");
			}
		}
		photoURLs.add(photoURL);
		photoURLsLoader.saveFile(photoURLs);
		return photoURL;
	}
	
	private boolean isOnlyPhotoAttachment(GetResponse response,Integer element){
		//indicates that this post has only photo and nothing more
		if(response.getItems().get(element).getAttachments() == null){
			return false;
		}
		if(			response.getItems().get(element).getAttachments().size() == 1 //только один attachment
				&& response.getItems().get(element).getAttachments().get(0).getPhoto() != null //и этот attachment фото
				&&  response.getItems().get(element).getText().equals("")) // текста быть не должно
			return true;
		else return false;
	}

	@Override
	public void doRepostAndSavePhotoOnHardDisk(String destination, String access_token, String group_id, String user_id,
			PhotoSize photoSize, Long delayInMills, Integer countOfPosts, String... groups)
			throws MalformedURLException {
		String uploadURL = RESTOperations.getWallUploadServer(group_id,access_token).getUploadUrl();
		StringBuilder owner_id = new StringBuilder();
		owner_id.append("-");
		owner_id.append(group_id);
		int success = 0;
		int filtered = 0;
		
		for(int g = 0;g<groups.length;g++){
			GetResponse response = RESTOperations.getWallGroup(groups[g], countOfPosts+1);
			if(response == null)
			{
				log.send(_nullMessage);
				break;
			}
		success = 0;
		filtered = 0;
		
		for(int i = 1; i < countOfPosts+1; i++){
			String photoURL = new String();
			if(!this.isOnlyPhotoAttachment(response,i)){
				filtered++;
				continue;
			}
			//define photo url depending from resolution
			try{
			switch(photoSize){
			case PHOTO_1280:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto1280());
			break;
			case PHOTO_807:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto807());
			break;
			case PHOTO_604:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto604());
			break;
			case PHOTO_130:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto130());
			break;
			case PHOTO_75:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto75());
			break;
			}
			}catch(DuplicateUrlException de){
				logger.error(de.getMessage());
				log.send(de.getMessage());
				filtered++;
				continue;
			}catch(NullPointerException npe){
				logger.error(_nullPhoto);
				log.send(_nullPhoto);
				continue;
			}
			
			this.savePhoto(photoURL, destination);
			
			Photo[] posted = RESTOperations.saveWallPhoto(
					user_id,
					group_id, 
					access_token, 
					RESTOperations.postPhoto(
							uploadURL,
							photoURL));
			
			RESTOperations.postPhotoToWall(
					access_token,
					owner_id.toString(),
					Integer.toString(posted[0].getId()),
					Integer.toString(posted[0].getOwnerId()));
			success++;
			  try {
				Thread.sleep(delayInMills);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			  
		}
		StringBuilder summaryInfo = new StringBuilder();
		summaryInfo.append("ВСЕГО: успешно: ");
		summaryInfo.append(success);
		summaryInfo.append(" отфильтровано: ");
		summaryInfo.append(filtered);
		String string = summaryInfo.toString();
		logger.info(string);
		log.send(string);
		System.out.println(string);
		}
	}
	
	private void savePhoto(String photoUrl, String destination){
		URLConnection fileStream = null;
		StringBuilder finalDestination = new StringBuilder();
		finalDestination.append(destination);
		finalDestination.append("vkphoto");
		finalDestination.append(Math.floor(Math.random()*1000000));
		finalDestination.append(".jpg");
		try{
			URL remoteFile = new URL(photoUrl);
			fileStream = remoteFile.openConnection();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		Path path = Paths.get(finalDestination.toString());
		
		try(OutputStream fOut=Files.newOutputStream(path);
			InputStream in = fileStream.getInputStream();){
			StringBuilder logsb = new StringBuilder();
			logsb.append("Downloding file from ");
			logsb.append(photoUrl);
			logsb.append(" Please wait.");
			String logToString = logsb.toString();
			logger.info(logToString);
			
			int data;
			while((data = in.read()) != -1){
				fOut.write(data);
			}
			logsb.delete(0, logsb.length());
			logsb.append("Finished downloading the file. It's located at ");
			logsb.append(path.toAbsolutePath());
			logToString = logsb.toString();
			
			logger.info(logToString);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void doRepostWithoutContentFilter(String access_token, String group_id, String user_id, PhotoSize photoSize,
			Long delayInMills, Integer countOfPosts, String... groups) throws MalformedURLException {
		String uploadURL = RESTOperations.getWallUploadServer(group_id,access_token).getUploadUrl();
		StringBuilder owner_id = new StringBuilder();
		owner_id.append("-");
		owner_id.append(group_id);
		int success = 0;
		
		for(int g = 0;g<groups.length;g++){
			GetResponse response = RESTOperations.getWallGroup(groups[g], countOfPosts+1);
			if(response == null)
			{
				log.send(_nullMessage);
				break;
			}
		success = 0;
		
		for(int i = 1; i < countOfPosts+1; i++){
			String photoURL = new String();
			
			Photo photo = null;
			List<WallpostAttachment> wa = response.getItems().get(i).getAttachments();
			
			//define photo url depending from resolution
			try{
				for(int k = 0; k<wa.size(); k++){
					if(wa.get(k).getPhoto() != null)
					photo = wa.get(k).getPhoto();
					
				}
				
			switch(photoSize){
			case PHOTO_1280:photoURL=assertNoEqualsAndAdd(photo.getPhoto1280());
			break;
			case PHOTO_807:photoURL=assertNoEqualsAndAdd(photo.getPhoto807());
			break;
			case PHOTO_604:photoURL=assertNoEqualsAndAdd(photo.getPhoto604());
			break;
			case PHOTO_130:photoURL=assertNoEqualsAndAdd(photo.getPhoto130());
			break;
			case PHOTO_75:photoURL=assertNoEqualsAndAdd(photo.getPhoto75());
			break;
			}
			}catch(DuplicateUrlException de){
				logger.error(de.getMessage());
				log.send(de.getMessage());
				continue;
			}catch(NullPointerException npe){
				logger.error(_nullPhoto);
				log.send(_nullPhoto);
				continue;
			}
			
			
			
			Photo[] posted = RESTOperations.saveWallPhoto(
					user_id,
					group_id, 
					access_token, 
					RESTOperations.postPhoto(
							uploadURL,
							photoURL));
			
			RESTOperations.postPhotoToWall(
					access_token,
					owner_id.toString(),
					Integer.toString(posted[0].getId()),
					Integer.toString(posted[0].getOwnerId()));
			success++;
			  try {
				Thread.sleep(delayInMills);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			  
		}
		StringBuilder summaryInfo = new StringBuilder();
		summaryInfo.append("ВСЕГО: успешно: ");
		summaryInfo.append(success);
		summaryInfo.append(" отфильтровано: фильтр был выключен.");
		String summStr = summaryInfo.toString();
		logger.info(summStr);
		log.send(summStr);
		
		}
		
		
	}

	@Override
	public void doRepostAndSavePhotoOnHardDiskWithoutContentFilter(String destination, String access_token,
			String group_id, String user_id, PhotoSize photoSize, Long delayInMills, Integer countOfPosts,
			String... groups) throws MalformedURLException {
		String uploadURL = RESTOperations.getWallUploadServer(group_id,access_token).getUploadUrl();
		StringBuilder owner_id = new StringBuilder();
		owner_id.append("-");
		owner_id.append(group_id);
		int success = 0;
		
		for(int g = 0;g<groups.length;g++){
			GetResponse response = RESTOperations.getWallGroup(groups[g], countOfPosts+1);
			if(response == null)
			{
				log.send(_nullMessage);
				break;
			}
			success = 0;
		
		for(int i = 1; i < countOfPosts+1; i++){
			String photoURL = new String();
			
			//define photo url depending from resolution
			try{
			switch(photoSize){
			case PHOTO_1280:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto1280());
			break;
			case PHOTO_807:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto807());
			break;
			case PHOTO_604:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto604());
			break;
			case PHOTO_130:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto130());
			break;
			case PHOTO_75:photoURL=assertNoEqualsAndAdd(response.getItems().get(i).getAttachments().get(0).getPhoto().getPhoto75());
			break;
			}
			}catch(DuplicateUrlException de){
				logger.error(de.getMessage());
				log.send(de.getMessage());
				continue;
			}catch(NullPointerException npe){
				logger.error(_nullPhoto);
				log.send(_nullPhoto);
				continue;
			}
			
			this.savePhoto(photoURL, destination);
			
			Photo[] posted = RESTOperations.saveWallPhoto(
					user_id,
					group_id, 
					access_token, 
					RESTOperations.postPhoto(
							uploadURL,
							photoURL));
			
			RESTOperations.postPhotoToWall(
					access_token,
					owner_id.toString(),
					Integer.toString(posted[0].getId()),
					Integer.toString(posted[0].getOwnerId()));
			success++;
			  try {
				Thread.sleep(delayInMills);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			  
		}
		StringBuilder summaryInfo = new StringBuilder();
		summaryInfo.append("ВСЕГО: успешно: ");
		summaryInfo.append(success);
		summaryInfo.append(" отфильтровано: фильтр был выключен.");
		
		String summStr = summaryInfo.toString();
		logger.info(summStr);
		log.send(summStr);
		}
	}
	
	private String getPath(){
		StringBuilder url = new StringBuilder();
		url.append(System.getProperty("user.dir"));
		url.append("/alreadyposted.url");
		return url.toString();
	}
}

package services;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import objects.IterationPOJO;
import websocket.GetLogger;
import websocket.LoggerWebSocketServlet;

public class Iteration{

	static final Logger logger = LogManager.getLogger(Iteration.class.getName());
	private IterationPOJO newIteration;
	private RepostService repostService;
	private boolean kill;
	private LoggerWebSocketServlet log = GetLogger.getwsLogger();
	
	
	public Iteration(IterationPOJO newIteration,RepostService repostService){
		this.newIteration = newIteration;
		this.repostService = repostService;
		kill = false;
		}
	
	public void start() {
		
		Timer timer = new Timer();
    	
    	TimerTask minutesTask = new TimerTask(){

			public void run() {
				try {
					boolean emptyDestination = 
							newIteration.getSavedPhotoDestination() == null || newIteration.getSavedPhotoDestination().isEmpty();
				if(!newIteration.isFilter() && emptyDestination) {
					log("Старт (фильтра нет, сохранения нет).");
					doRepostWithoutContentFilter();
				} else if(!newIteration.isFilter() && !emptyDestination){
					log("Старт (фильтра нет, сохранение).");
					doRepostAndSavePhotoOnHardDiskWithoutContentFilter();
				}else if(newIteration.isFilter() && emptyDestination){
					log("Старт (фильтр, сохранения нет).");
					doRepost();
				}else if(newIteration.isFilter() && !emptyDestination){
					log("Старт (фильтр, сохранение).");
					doRepostAndSavePhotoOnHardDisk();
				}
				
				} catch (Exception e) {
					log(e.getMessage());
					timer.cancel();
					e.printStackTrace();
				}
				if(kill) timer.cancel();
			}
		}; 
		
		timer.scheduleAtFixedRate(minutesTask, 0, newIteration.getGlobalDelay());
	}

	
	public void kill() {
		kill = true;
		log("Приостановленно.");
	}
	
	public void wakeUp(){
		kill = false;
		start();
		log("активна.");
	}
	
	private void doRepostWithoutContentFilter() throws Exception{
		repostService.doRepostWithoutContentFilter(
				newIteration.getAccess_token(),
				newIteration.getGroup_id(),
				newIteration.getUser_id(),
				newIteration.getPhotoSize(),
				newIteration.getDelay(),
				newIteration.getCountOfPosts(),
				newIteration.getGroupsFrom());
	}
	
	private void doRepostAndSavePhotoOnHardDisk() throws Exception{
		repostService.doRepostAndSavePhotoOnHardDisk(
				newIteration.getSavedPhotoDestination(), 
				newIteration.getAccess_token(),
				newIteration.getGroup_id(),
				newIteration.getUser_id(),
				newIteration.getPhotoSize(),
				newIteration.getDelay(),
				newIteration.getCountOfPosts(),
				newIteration.getGroupsFrom());
	}
	
	private void doRepost() throws Exception{
		repostService.doRepost(
				newIteration.getAccess_token(),
				newIteration.getGroup_id(),
				newIteration.getUser_id(),
				newIteration.getPhotoSize(),
				newIteration.getDelay(),
				newIteration.getCountOfPosts(),
				newIteration.getGroupsFrom());
	}
	
	private void doRepostAndSavePhotoOnHardDiskWithoutContentFilter() throws Exception{
		repostService.doRepostAndSavePhotoOnHardDiskWithoutContentFilter(
				newIteration.getSavedPhotoDestination(),
				newIteration.getAccess_token(),
				newIteration.getGroup_id(),
				newIteration.getUser_id(),
				newIteration.getPhotoSize(),
				newIteration.getDelay(),
				newIteration.getCountOfPosts(),
				newIteration.getGroupsFrom());
	}
	
	private void log(String message){
		StringBuilder log = new StringBuilder();
		log.append("Итерация с id ");
		log.append(newIteration.getId());
		log.append(": ");
		log.append(message);
		String logString = log.toString();
		logger.info(logString);
		this.log.send(logString);
	}

	public IterationPOJO getIterationPOJO() {
		return newIteration;
	}

}

package rest;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import enums.Order;
import enums.OriginType;
import enums.SortBy;
import exceptions.InternalServerException;
import exceptions.NonexistentDomainException;
import objects.Message;
import persistence.MessagesRepository.Archived;
import services.MessagesService;
import util.HTTPUtils;

@RestController
@RequestMapping(value="/api")
public class RESTMessagesController {

	private static final Logger logger = LogManager.getLogger(RESTMessagesController.class.getName());
	
	@Autowired
	private MessagesService messagesService;
	
	@Autowired
	private Gson gson;
	
	@RequestMapping(value="/getUserMessages/{userId}",method=RequestMethod.GET,produces ="application/json; charset=UTF-8")
	public ResponseEntity<String> getUserMessages(
			@RequestParam(value = "sortBy", required = false) String sortByParam,
			@RequestParam(value = "order", required = false) String orderParam,
			@RequestParam(value = "count", required = false) String countParam,
			@RequestParam(value = "skip", required = false) String skipParam,
			@RequestParam(value = "archived", required = false) String archivedParam,
			@PathVariable() String userId){
		
		long id = HTTPUtils.parseLong(userId);
		
		SortBy sort = SortBy.RATING;
		Order order = Order.DESCENDING;
		int count = 12;
		int skip = 0;
		Archived archived = Archived.IGNORE;
		
		if(orderParam != null && orderParam.equals("ASC")){
			logger.info("Incoming order param is not null. Its value is ASC");
			order = Order.ASCENDING;
		}
		
		if(sortByParam != null && sortByParam.equals("DATE")){
			logger.info("Incoming sortBy param is not null. Its value is DATE");
			sort = SortBy.DATE;
		}
		
		if(countParam != null ) {
			logger.info("Incoming count param is not null. Its value is -> ".concat(countParam));
			count = HTTPUtils.parseInteger(0,49,countParam);
		}
		
		if(skipParam != null ) {
			logger.info("Incoming skip param is not null. Its value is -> ".concat(skipParam));
			skip = HTTPUtils.parseInteger(0,Integer.MAX_VALUE,skipParam);
		}
		
		if(archivedParam != null){
			logger.info("Incoming archived param is not null. Its value is "+archivedParam);
			archived = (Archived) HTTPUtils.parseEnum(archivedParam, Archived.values());
		}
		
		List<Message> list  = messagesService.getAllMessagesForUser(id, archived, sort, order, count, skip);
		
		if(list == null) throw new InternalServerException("Internal server error.");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json; charset=UTF-8");
		ResponseEntity<String> response = new ResponseEntity<String>(
				gson.toJson(list),
				headers,
				HttpStatus.OK);
		
		return response;
	}
	
	@RequestMapping(value="/getMessages/{content}/{contentId}",method=RequestMethod.GET,produces ="application/json; charset=UTF-8")
	public ResponseEntity<String> getMessages(
			@RequestParam(value = "sortBy", required = false) String sortByParam,
			@RequestParam(value = "order", required = false) String orderParam,
			@RequestParam(value = "count", required = false) String countParam,
			@RequestParam(value = "skip", required = false) String skipParam,
			@RequestParam(value = "archived", required = false) String archivedParam,
			@PathVariable() String content,
			@PathVariable() String contentId){
		
		long id = HTTPUtils.parseLong(contentId);
		OriginType originType = (OriginType) 
				HTTPUtils.parseEnum(content, OriginType.POSTED_CONTENT,OriginType.USER_PROFILE);
		
		if(originType == null)	throw new NonexistentDomainException("Unknown source");
		
		SortBy sort = SortBy.RATING;
		Order order = Order.DESCENDING;
		int count = 12;
		int skip = 0;
		Archived archived = Archived.IGNORE;
		
		if(orderParam != null && orderParam.equals("ASC")){
			logger.info("Incoming order param is not null. Its value is ASC");
			order = Order.ASCENDING;
		}
		
		if(sortByParam != null && sortByParam.equals("DATE")){
			logger.info("Incoming sortBy param is not null. Its value is DATE");
			sort = SortBy.DATE;
		}
		
		if(countParam != null ) {
			logger.info("Incoming count param is not null. Its value is -> ".concat(countParam));
			count = HTTPUtils.parseInteger(0,49,countParam);
		}
		
		if(skipParam != null ) {
			logger.info("Incoming skip param is not null. Its value is -> ".concat(skipParam));
			skip = HTTPUtils.parseInteger(0,Integer.MAX_VALUE,skipParam);
		}
		
		if(archivedParam != null){
			logger.info("Incoming archived param is not null. Its value is "+archivedParam);
			archived = (Archived) HTTPUtils.parseEnum(archivedParam, Archived.values());
		}
		
		List<Message> list = messagesService.getAllMessages(originType, id, archived, sort, order, count, skip);
		
		if(list == null) throw new InternalServerException("Internal server error.");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json; charset=UTF-8");
		ResponseEntity<String> response = new ResponseEntity<String>(
				gson.toJson(list),
				headers,
				HttpStatus.OK);
		
		return response;
	}
	
}

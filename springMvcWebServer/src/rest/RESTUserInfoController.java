package rest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import services.UsersInfoService;

@RestController
@RequestMapping("/api")
public class RESTUserInfoController {

	private static final Logger logger = LogManager.getLogger(RESTUserInfoController.class.getName());
	
	@Autowired 
	private UsersInfoService usersInfoService;
	
	@Autowired
	private Gson gson;
	
	@RequestMapping(value="/lastLoggedIn",method=RequestMethod.GET,produces ="application/json")
	public ResponseEntity<String> lastLoggedIn(){
		String json = gson.toJson(usersInfoService.getAllWhoLoggedIn());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		ResponseEntity<String> response = 
				new ResponseEntity<String>(json,headers,HttpStatus.OK);
		logger.info("10 last users who logged in are returning.");
		return response;
	}
	
	@RequestMapping(value="/lastSignUp",method=RequestMethod.GET,produces ="application/json")
	public ResponseEntity<String> lastSignUp(){
		String json = gson.toJson(usersInfoService.getAllWhoRegistered());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		ResponseEntity<String> response = 
				new ResponseEntity<String>(json,headers,HttpStatus.OK);
		logger.info("10 last users who signed in are returning.");
		return response;
	}
	
}

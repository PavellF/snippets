package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import objects.IterationPOJO;
import objects.PhotoSize;
import services.Iteration;
import services.IterationManager;
import websocket.GetLogger;
import websocket.LoggerWebSocketServlet;


@SuppressWarnings("serial")
public class MainPageServlet extends HttpServlet{

	static final Logger logger = LogManager.getLogger(MainPageServlet.class.getName());
	public static final String PAGE_URL = "/";
	private LoggerWebSocketServlet log = GetLogger.getwsLogger();
	private IterationManager iterationManager;
	private boolean firstRun = true;
	
	public MainPageServlet(IterationManager iterationManager){
		this.iterationManager = iterationManager;
		startIterations();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		
		String pathToStyle = request.getParameter("pathToStyle");
		Map<String, Object> variables = new HashMap<String, Object>();
		
		if(pathToStyle == null || pathToStyle.isEmpty())
			pathToStyle = "/style.css";
		
		variables.put("pathToStyle", pathToStyle);
		
		response.setContentType("text/html;charset=utf-8;");
		response.setStatus(HttpServletResponse.SC_OK);
        
		response.getWriter().println(PageLoader.instance().getPage("panel.html", variables));
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		IterationPOJO iteration = new IterationPOJO();
		try{
		iteration.setAccess_token(request.getParameter("access_token"));
		iteration.setGroup_id(request.getParameter("group_id"));
		iteration.setUser_id(request.getParameter("user_id"));
		iteration.setDelay(Long.parseLong(request.getParameter("delay")));
		iteration.setCountOfPosts(Integer.parseInt(request.getParameter("countOfPosts")));
		iteration.setGlobalDelay(Long.parseLong(request.getParameter("globalDelay")));
		iteration.setPhotoSize(getPhotoSize(request.getParameter("photo_size")));
		iteration.setFilter(getFilterValue(request.getParameter("filter")));
		iteration.setSavedPhotoDestination(request.getParameter("savedPhotoDestination"));
		iteration.setGroupsFrom(getGroupsFrom(request.getParameter("groupsFrom")));
		}catch(NumberFormatException nfe){
			log.send("Something went wrong.");
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			return;
		}
		this.iterationManager.startIteration(iteration);
		
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect("/");
	}
	
	private void startIterations(){
		try{
		if(firstRun){
			iterationManager.load().values().forEach(
					(Iteration iteration) -> {
						iteration.getIterationPOJO().setActive(false);
						logger.info("Successfully loaded.");
					});
			firstRun = false;
		}
		} catch(NullPointerException npe){
			logger.error("File does not exist.");
		}
	}
	
		
	private PhotoSize getPhotoSize(String var){
		switch(var){
		case "1280" : return PhotoSize.PHOTO_1280;
		case "807"  : return PhotoSize.PHOTO_807;
		case "604"  : return PhotoSize.PHOTO_604;
		case "130"  : return PhotoSize.PHOTO_130;
		case "75"   : return PhotoSize.PHOTO_75;
		default     : return PhotoSize.PHOTO_604;
		}
	}
	
	private boolean getFilterValue(String var){
		if(var == null || var.equals("off")) return false;
		else return true;
	}
	
	private String[] getGroupsFrom(String var){
		return var.split("@");
	}
}

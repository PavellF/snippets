package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.IterationPOJO;
import objects.PhotoSize;
import services.IterationManager;

@SuppressWarnings("serial")
public class ChangePrefsServlet extends HttpServlet{

	private IterationManager iterationManager;
	public static final String PAGE_URL = "/changeprefs";
	
	public ChangePrefsServlet(IterationManager iterationManager){
		this.iterationManager = iterationManager;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("pathToStyle", "/style.css");
		
		try{
			int id = Integer.parseInt(request.getParameter("id"));
			IterationPOJO iterationPOJO = iterationManager.getIterationList().get(id).getIterationPOJO();
			
			variables.put("id", id);
			variables.put("access_token", iterationPOJO.getAccess_token());
			variables.put("group_id", iterationPOJO.getGroup_id());
			variables.put("user_id", iterationPOJO.getUser_id());
			variables.put("delay", iterationPOJO.getDelay());
			variables.put("countOfPosts", iterationPOJO.getCountOfPosts());
			variables.put("globalDelay", iterationPOJO.getGlobalDelay());
			variables.put("savedPhotoDestination", iterationPOJO.getSavedPhotoDestination());
			variables.put("groupsFrom", parseArray(iterationPOJO.getGroupsFrom()));
			
			response.setContentType("text/html;charset=utf-8;");
			response.setStatus(HttpServletResponse.SC_OK);
	        response.getWriter().println(PageLoader.instance().getPage("changepanel.html", variables));
			
		}catch(NumberFormatException | NullPointerException e){
			response.sendRedirect("/");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		IterationPOJO iteration = new IterationPOJO();
		try{
		iteration.setId(Integer.parseInt(request.getParameter("id")));
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
			response.sendRedirect("/");
			nfe.printStackTrace();
		}
		this.iterationManager.restartIteration(iteration);
		response.setStatus(HttpServletResponse.SC_OK);
		
		response.sendRedirect("/");
	}
	
	private String parseArray(String[] array){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<array.length; i++){
			sb.append(array[i]);
			sb.append("\n@\n");
		}
		return sb.toString();
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

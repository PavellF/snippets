package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import objects.IterationPOJO;
import services.Iteration;
import services.IterationManager;

@SuppressWarnings("serial")
public class IterationPOJOArrayServlet extends HttpServlet{

	private IterationManager iterationManager;
	private final Gson gson = new Gson();
	public static final String PAGE_URL = "/getjsonarray";
	
	public IterationPOJOArrayServlet(IterationManager iterationManager){
		this.iterationManager = iterationManager;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		
		if(!(iterationManager.getIterationList().isEmpty()) || !(iterationManager.getIterationList() == null))
		{
			response.setContentType("application/json;charset=utf-8;");
		response.setStatus(HttpServletResponse.SC_OK);
        
		response.getWriter().println(iterationPOJOListToJson());
	}else{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}
		}
	
	private String iterationPOJOListToJson(){
		List<IterationPOJO> list = new ArrayList<IterationPOJO>();
		iterationManager.getIterationList().values().forEach(
				(Iteration i) -> {list.add(i.getIterationPOJO());});
		return gson.toJson(list);
	}

}

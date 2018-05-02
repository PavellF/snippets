package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import objects.IterationPOJO;
import services.IterationManager;

@SuppressWarnings("serial")
public class ChangeStatusServlet extends HttpServlet{

	private IterationManager iterationManager;
	public static final String PAGE_URL = "/changestatus";
	
	public ChangeStatusServlet(IterationManager iterationManager){
		this.iterationManager = iterationManager;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{

		try{
			int id = Integer.parseInt(request.getParameter("id"));
			IterationPOJO iterationPOJO = iterationManager.getIterationList().get(id).getIterationPOJO();
			
			if(iterationPOJO.isActive())iterationManager.pause(id);
				else iterationManager.resume(id);
			
			response.setStatus(HttpServletResponse.SC_OK);
			response.sendRedirect("/");
		}catch(NumberFormatException | NullPointerException e){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			response.sendRedirect("/");
		}
	}
}

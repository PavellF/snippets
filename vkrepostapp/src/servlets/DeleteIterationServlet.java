package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.IterationManager;

@SuppressWarnings("serial")
public class DeleteIterationServlet extends HttpServlet{

	private IterationManager iterationManager;
	public static final String PAGE_URL = "/doDelete";
	
	public DeleteIterationServlet(IterationManager iterationManager){
		this.iterationManager = iterationManager;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{

		try{
			int id = Integer.parseInt(request.getParameter("id"));
			iterationManager.delete(id);
			response.setStatus(HttpServletResponse.SC_OK);
			response.sendRedirect("/");
		}catch(NumberFormatException | NullPointerException e){
			response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			response.sendRedirect("/");
		}
	}
}

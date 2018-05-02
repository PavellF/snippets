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

@SuppressWarnings("serial")
public class HelpPageServlet extends HttpServlet{

	static final Logger logger = LogManager.getLogger(MainPageServlet.class.getName());
	public static final String PAGE_URL = "/help";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		String pathToStyle = request.getParameter("pathToStyle");
		Map<String, Object> variables = new HashMap<String, Object>();
		
		if(pathToStyle == null || pathToStyle.isEmpty())
			pathToStyle = "/style.css";
		
		variables.put("pathToStyle", pathToStyle);
		
		response.setContentType("text/html;charset=utf-8;");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(PageLoader.instance().getPage("helppage.html", variables));
		
	}

	
	
	
	
}

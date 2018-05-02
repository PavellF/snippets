package servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CssLoaderServlet extends HttpServlet{

	private  String pathToCssFile;
	private Map<String, Object> variables;
	
	public CssLoaderServlet(String pathToCssFile,Map<String, Object> variables){
		this.pathToCssFile = pathToCssFile;
		this.variables = variables;
	}
	
	public CssLoaderServlet(String pathToCssFile){
		this.pathToCssFile = pathToCssFile;
		this.variables = null;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		response.setContentType("text/css;charset=utf-8;");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(PageLoader.instance().getPage(pathToCssFile, variables));
		
	}
	
	
}

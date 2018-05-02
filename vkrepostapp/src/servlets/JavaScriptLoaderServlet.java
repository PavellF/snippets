package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class JavaScriptLoaderServlet extends HttpServlet{

	private  String pathToJsFile;
	
	public JavaScriptLoaderServlet(String pathToJsFile){
		this.pathToJsFile = pathToJsFile;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		response.setContentType("text/javascript;charset=utf-8;");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(PageLoader.instance().getPage(pathToJsFile, null));
		
	}
}

package servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import objects.PreferredBackground;
import services.SerializableLoader;

@SuppressWarnings("serial")
public class StyleCSSLoaderServlet extends HttpServlet{

	static final Logger logger = LogManager.getLogger(MainPageServlet.class.getName());
	public static final String PAGE_URL = "/style.css";
	private static final String PATH_TO_PREFERRED_BACKGROUND_FILE = pathToPreferredBackgroundFile();
	private SerializableLoader<PreferredBackground> serializableLoader 
	= new SerializableLoader<PreferredBackground>(PATH_TO_PREFERRED_BACKGROUND_FILE);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{

		String background = request.getParameter("background");
		Map<String, Object> cssVar = new HashMap<String, Object>();
		response.setContentType("text/css;charset=utf-8;");
		
		if (background == null || background.isEmpty()) {
			background = loadPreferredBackground().getPreferredBackground();
            response.setStatus(HttpServletResponse.SC_OK);
		} else {
			PreferredBackground preferredBackground = new PreferredBackground();
			preferredBackground.setPreferredBackground(background);
			this.serializableLoader.saveFile(preferredBackground);
            response.setStatus(HttpServletResponse.SC_OK);
		}
		
		
		cssVar.put("backgroundURL", background);
        
		response.getWriter().println(PageLoader.instance().getPage("style.css", cssVar));
		
		
	}
	
	private PreferredBackground loadPreferredBackground(){
		
		if(Files.exists(Paths.get(PATH_TO_PREFERRED_BACKGROUND_FILE))){
			
			return serializableLoader.loadFile();
        	
		}else{
			PreferredBackground preferredBackground = new PreferredBackground();
			preferredBackground.setPreferredBackground("/wall98620");
			serializableLoader.saveFile(preferredBackground);
			return preferredBackground;
		}
	}
	
	private static String pathToPreferredBackgroundFile(){
		StringBuilder path = new StringBuilder();
		path.append(System.getProperty("user.dir"));
		path.append("/pref.prop");
		return path.toString();
	}
}

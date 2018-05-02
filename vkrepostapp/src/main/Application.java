package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import services.IterationManager;
import services.IterationManagerImpl;
import services.RESTOperations;
import services.RESTOperationsImpl;
import services.RepostService;
import services.RepostServiceImpl;
import servlets.CssLoaderServlet;
import servlets.DeleteIterationServlet;
import servlets.HelpPageServlet;
import servlets.ImageLoaderServlet;
import servlets.IterationPOJOArrayServlet;
import servlets.JavaScriptLoaderServlet;
import servlets.MainPageServlet;
import servlets.ChangePrefsServlet;
import servlets.ChangeStatusServlet;
import servlets.StyleCSSLoaderServlet;
import websocket.GetLogger;
import websocket.LoggerWebSocketServlet;

public class Application {
	
	static final Logger logger = LogManager.getLogger(Application.class.getName());
	
	public void start(int port) throws Exception{
		logger.info("Starting at http://127.0.0.1:"+port);
		ServletContextHandler sch = new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		this.loadlogger(sch);
		this.loadPages(sch);
		this.loadJavaScript(sch);
		this.loadCss(sch);
		this.loadPictures(sch);
		
		Server server = new Server(port);
		
		ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("WebContent/WEB-INF/views/");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{sch,resource_handler});
        server.setHandler(handlers);
		
		server.start();
		logger.info("Server started");
		server.join();
	}
	
	private void loadCss(ServletContextHandler sch){
		sch.addServlet(new ServletHolder(new StyleCSSLoaderServlet()),StyleCSSLoaderServlet.PAGE_URL);
		sch.addServlet(new ServletHolder(new CssLoaderServlet("/bootstrap-3.3.5-dist/css/bootstrap.css")),"/bootstrap-dist/css/bootstrap.css");
	}
	
	private void loadPages(ServletContextHandler sch){
		RESTOperations RESTOperations = new RESTOperationsImpl();
		RepostService repostService = new RepostServiceImpl(RESTOperations);
		IterationManager IterationManager = new IterationManagerImpl(repostService);
		
		sch.addServlet(new ServletHolder(new ChangeStatusServlet(IterationManager)), ChangeStatusServlet.PAGE_URL);
		sch.addServlet(new ServletHolder(new HelpPageServlet()), HelpPageServlet.PAGE_URL);
		sch.addServlet(new ServletHolder(new MainPageServlet(IterationManager)),MainPageServlet.PAGE_URL);
		sch.addServlet(new ServletHolder(new IterationPOJOArrayServlet(IterationManager)), IterationPOJOArrayServlet.PAGE_URL);
		sch.addServlet(new ServletHolder(new DeleteIterationServlet(IterationManager)), DeleteIterationServlet.PAGE_URL);
		sch.addServlet(new ServletHolder(new ChangePrefsServlet(IterationManager)), ChangePrefsServlet.PAGE_URL);
	}
	
	private void loadPictures(ServletContextHandler sch){
		sch.addServlet(new ServletHolder(new ImageLoaderServlet("WebContent/WEB-INF/views/walls/wallhaven-161984.jpg")), "/wall161984");
		sch.addServlet(new ServletHolder(new ImageLoaderServlet("WebContent/WEB-INF/views/walls/wallhaven-203199.jpg")), "/wall203199");
		sch.addServlet(new ServletHolder(new ImageLoaderServlet("WebContent/WEB-INF/views/walls/wallhaven-226195.jpg")), "/wall226195");
		sch.addServlet(new ServletHolder(new ImageLoaderServlet("WebContent/WEB-INF/views/walls/wallhaven-240158.jpg")), "/wall240158");
		sch.addServlet(new ServletHolder(new ImageLoaderServlet("WebContent/WEB-INF/views/walls/wallhaven-98620.jpg")), "/wall98620");
	}
	
	private void loadJavaScript(ServletContextHandler sch){
		sch.addServlet(new ServletHolder(new JavaScriptLoaderServlet("/jquery-3.1.1.js")), "/jquery");
	}
	
	private void loadlogger(ServletContextHandler sch){
		sch.addServlet(new ServletHolder(GetLogger.getwsLogger()), LoggerWebSocketServlet.PAGE_URL);
		
	}
}

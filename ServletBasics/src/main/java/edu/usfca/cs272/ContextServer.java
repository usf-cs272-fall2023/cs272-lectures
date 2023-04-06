package edu.usfca.cs272;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Demonstrates how to use servlet contexts to configure which servlets handle
 * which requests.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class ContextServer {
	/** The hard-coded port to run this server. */
	public static final int PORT = 8080;

	/**
	 * The logger to use (Jetty is configured via the pom.xml to use Log4j2 as well)
	 */
	public static Logger log = LogManager.getLogger();

	/**
	 * Sets up a Jetty server with two different servlet context handlers.
	 *
	 * @param args unused
	 * @throws Exception if unable to start and run server
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		// Setup first context for files
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setResourceBase(".");

		ContextHandler resourceContext = new ContextHandler("/resources");
		resourceContext.setHandler(resourceHandler);

		// Setup second context for servlets
		ServletContextHandler servletContext = new ServletContextHandler();
		servletContext.setContextPath("/servlets");

		servletContext.addServlet(HelloServer.HelloServlet.class, "/hello");
		servletContext.addServlet(TodayServer.TodayServlet.class, "/today");
		servletContext.addServlet(VisitServer.VisitServlet.class, "/");

		// Setup third context for default servlet handler
		DefaultHandler defaultHandler = new DefaultHandler();
		defaultHandler.setServeIcon(true);
		defaultHandler.setShowContexts(true);

		ContextHandler defaultContext = new ContextHandler();
		defaultContext.setContextPath("/");
		defaultContext.setHandler(defaultHandler);

		// Setup order of handlers
		HandlerList handlers = new HandlerList();
		handlers.addHandler(resourceContext);
		handlers.addHandler(servletContext);
		handlers.addHandler(defaultContext);

		server.setHandler(handlers);
		server.start();

		log.info("Server: {} with {} threads", server.getState(), server.getThreadPool().getThreads());
		server.join();

		/*
		 * Examples:
		 *
		 * http://localhost:8080/resources
		 * http://localhost:8080/resources/src
		 * http://localhost:8080/resources/nowhere
		 * http://localhost:8080/resources/hello
		 *
		 * http://localhost:8080/servlets/hello
		 * http://localhost:8080/servlets/today
		 * http://localhost:8080/servlets/nowhere
		 * http://localhost:8080/servlets/src/
		 * http://localhost:8080/servlets/
		 *
		 * http://localhost:8080/
		 * http://localhost:8080/favicon.ico
		 * http://localhost:8080/nowhere
		 * http://localhost:8080/hello
		 */
	}
}

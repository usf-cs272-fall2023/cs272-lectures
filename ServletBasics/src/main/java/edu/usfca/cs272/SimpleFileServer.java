package edu.usfca.cs272;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * A simple example of using Jetty and servlets to both serve static resources
 * and dynamic content.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class SimpleFileServer {
	/** The hard-coded port to run this server. */
	public static final int PORT = 8080;

	/**
	 * The logger to use (Jetty is configured via the pom.xml to use Log4j2 as well)
	 */
	public static Logger log = LogManager.getLogger();

	/**
	 * Sets up a Jetty server with both a resource and servlet handler. Able to
	 * respond with static and dynamic content.
	 *
	 * @param args unused
	 * @throws Exception if unable to start server
	 */
	public static void main(String[] args) throws Exception {
		// Enable DEBUG logging (see debug.log file for messages)
		System.setProperty("org.eclipse.jetty.LEVEL", "DEBUG");

		Server server = new Server(PORT);

		// Add static resource holders to web server
		// This indicates where web files are accessible on the file system
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);

		// Try both .setResourceBase(".") and .setResourceBase("./src")
		resourceHandler.setResourceBase(".");

		// Can still assign servlets to specific requests
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(VisitServer.VisitServlet.class, "/visits");

		// Setup handlers (and handler order)
		HandlerList handlers = new HandlerList();
		handlers.addHandler(resourceHandler);
		handlers.addHandler(servletHandler);

		server.setHandler(handlers);
		server.start();

		log.info("Server: {} with {} threads", server.getState(), server.getThreadPool().getThreads());
		server.join();

		// http://localhost:8080/
		// http://localhost:8080/visits
		// http://localhost:8080/src/
	}
}

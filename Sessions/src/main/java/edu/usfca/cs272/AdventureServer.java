package edu.usfca.cs272;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Demonstrates how to use session tracking and enum types to create a simple
 * adventure game.
 *
 * @see AdventureServer
 * @see AdventureServlet
 * @see AdventureRoom
 * @see Direction
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class AdventureServer {
	/**
	 * Initializes and starts the adventure game server.
	 *
	 * @param args unused
	 * @throws Exception in unable to start or run server
	 */
	public static void main(String[] args) throws Exception {
		// type of handler that supports sessions
		ServletContextHandler servletContext = null;

		// turn on sessions and set context
		servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletContext.setContextPath("/");
		servletContext.addServlet(AdventureServlet.class, "/");

		// default handler for favicon.ico requests
		DefaultHandler defaultHandler = new DefaultHandler();
		defaultHandler.setServeIcon(true);

		// only handle requests for favicon
		ContextHandler defaultContext = new ContextHandler("/favicon.ico");
		defaultContext.setHandler(defaultHandler);

		// setup handler order
		HandlerList handlers = new HandlerList();
		handlers.addHandler(defaultContext);
		handlers.addHandler(servletContext);

		// setup jetty server
		Server server = new Server(8080);
		server.setHandler(handlers);
		server.start();
		server.join();
	}
}

package edu.usfca.cs272;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Demonstrates how to create a simple message board using Jetty and servlets,
 * as well as how to initialize servlets when you need to call its constructor.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class MessageServer {
	/** The hard-coded port to run this server. */
	public static final int PORT = 8080;

	/**
	 * Sets up a Jetty server with different servlet instances.
	 *
	 * @param args unused
	 * @throws Exception if unable to start and run server
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();

		// must use servlet holds when need to call a constructor
		handler.addServletWithMapping(new ServletHolder(new MessageServlet()), "/pie");
		handler.addServletWithMapping(new ServletHolder(new MessageServlet()), "/cake");
		handler.addServletWithMapping(new ServletHolder(new BulmaMessageServlet()), "/bulma");

		server.setHandler(handler);
		server.start();
		server.join();
	}
}

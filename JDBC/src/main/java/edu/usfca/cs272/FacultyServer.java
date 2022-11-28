package edu.usfca.cs272;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Demonstrates how to interact with a database server using JDBC and Jetty.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class FacultyServer {
	/**
	 * Creates a database connection and Jetty server to allow users to interact
	 * with the faculty tables from the Relational Databases examples.
	 *
	 * @param args first argument is used as the database.properties file if present
	 * @throws Exception if unable to start server
	 */
	public static void main(String[] args) throws Exception {
		String properties = "database.properties";

		// Check for other properties file in command-line arguments
		if (args.length > 0 && Files.exists(Path.of(args[0]))) {
			properties = args[0];
		}

		// Attempt to connect to database and create servlet
		DatabaseConnector connector = new DatabaseConnector(properties);

		// Do not continue if unable to connect to database
		if (!connector.testConnection()) {
			return;
		}

		// Default handler for favicon.ico requests
		ContextHandler defaultHandler = new ContextHandler("/favicon.ico");
		defaultHandler.setHandler(new DefaultHandler());

		// Main servlet handler
		FacultyServlet servlet = new FacultyServlet(connector);
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(new ServletHolder(servlet), "/");

		HandlerList handlers = new HandlerList();
		handlers.addHandler(defaultHandler);
		handlers.addHandler(servletHandler);

		// Only setup server if we made it this far (otherwise an exception thrown)
		Server server = new Server(8080);
		server.setHandler(handlers);
		server.start();
		server.join();
	}
}

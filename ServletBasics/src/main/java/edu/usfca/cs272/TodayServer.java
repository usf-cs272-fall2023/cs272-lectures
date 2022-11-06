package edu.usfca.cs272;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A simple example of using Jetty and servlets to create a dynamic web page.
 * The web page will display the current date/time when loaded.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class TodayServer {
	/** The hard-coded port to run this server. */
	public static final int PORT = 8080;

	/**
	 * The logger to use (Jetty is configured via the pom.xml to use Log4j2 as well)
	 */
	public static Logger log = LogManager.getLogger();

	/**
	 * Sets up a Jetty server with explicit connector and handler components.
	 *
	 * @param args unused
	 * @throws Exception if unable to start web server
	 */
	public static void main(String[] args) throws Exception {
		// Create the Jetty server
		Server server = new Server();

		// Setup the connector component
		ServerConnector connector = new ServerConnector(server);
		connector.setHost("localhost");
		connector.setPort(PORT);

		// Setup the handler component
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(TodayServlet.class, "/today");

		// Configure server to use connector and handler
		server.addConnector(connector);
		server.setHandler(handler);

		// Start the server (it is a thread)
		server.start();
		log.info("Server: {} with {} threads", server.getState(), server.getThreadPool().getThreads());

		// Keeps main thread active as long as server is active
		// Until we implement shutdown, the server will never terminate
		server.join();
	}

	/**
	 * A simple servlet that will display the current date and time when loaded.
	 */
	public static class TodayServlet extends HttpServlet {
		/** Class version for serialization, in [YEAR][TERM] format (unused). */
		private static final long serialVersionUID = 202208;

		/** Title of web page. */
		private static final String TITLE = "Today";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info(request.toString());

			String html = """
					<!DOCTYPE html>
					<html lang="en">

					<head>
					  <meta charset="utf-8">
					  <title>%1$s</title>
					</head>

					<body>
					<h1>%1$s</h1>
					<p>It is %2$s.</p>
					</body>
					</html>
					""";

			PrintWriter out = response.getWriter();
			out.printf(html, TITLE, getDate());

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}

	/**
	 * Returns the date and time in a long format. For example: "12:00 am on
	 * Saturday, January 01 2000".
	 *
	 * @return current date and time
	 */
	public static String getDate() {
		String format = "hh:mm:ss a 'on' EEEE, MMMM dd yyyy";
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return today.format(formatter);
	}
}

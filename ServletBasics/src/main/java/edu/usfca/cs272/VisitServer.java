package edu.usfca.cs272;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A simple example of using Jetty and servlets to track the number of visitors
 * to a web page, demonstrating that this code is run in a multi-threaded
 * setting.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class VisitServer {
	/** The hard-coded port to run this server. */
	public static final int PORT = 8080;

	/**
	 * The logger to use (Jetty is configured via the pom.xml to use Log4j2 as well)
	 */
	public static Logger log = LogManager.getLogger();

	// Keep in mind our server is multithreaded, so we need to use
	// storage safe for access by multiple threads simultaneously

	/**
	 * A thread-safe counter to track number of visits to this website since the
	 * server was started.
	 */
	private static AtomicInteger visits = new AtomicInteger();

	/**
	 * Sets up a Jetty server configured to use the servlets defined in this class.
	 *
	 * @param args unused
	 * @throws Exception if unable to start web server
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(VisitServlet.class, "/");

		server.setHandler(handler);
		server.start();

		log.info("Server: {} with {} threads", server.getState(), server.getThreadPool().getThreads());
		server.join();
	}

	/**
	 * Increments the number of visits to this website and outputs the current
	 * value.
	 */
	public static class VisitServlet extends HttpServlet {
		/** Class version for serialization, in [YEAR][TERM] format (unused). */
		private static final long serialVersionUID = 202208;

		/** The title to use for this webpage. */
		private static final String TITLE = "Visits";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info(request);

			// Check to make sure the browser is not requesting favicon.ico
			if (request.getRequestURI().endsWith("favicon.ico")) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			String html = """
					<!DOCTYPE html>
					<html lang="en">

					<head>
					  <meta charset="utf-8">
					  <title>%1$s</title>
					</head>

					<body>
					<h1>%1$s</h1>
					<p>There have been %2$d visits to this page.</p>
					<p>This request was handled by thread %3$s.</p>
					</body>
					</html>
					""";

			// Safely increment the number of visits to this website
			int current = visits.incrementAndGet();

			PrintWriter out = response.getWriter();
			out.printf(html, TITLE, current, Thread.currentThread().getName());

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}

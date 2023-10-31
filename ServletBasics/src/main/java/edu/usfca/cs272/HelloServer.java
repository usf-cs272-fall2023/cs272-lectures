package edu.usfca.cs272;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Demonstrates the danger of using user-input in a web application, especially
 * regarding cross-site scripting (XSS) attacks.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class HelloServer {
	/** The hard-coded port to run this server. Should make configurable! */
	public static final int PORT = 8080;

	/**
	 * The logger to use (Jetty is configured via the pom.xml to use Log4j2)
	 */
	public static Logger log = LogManager.getLogger();

	/**
	 * Sets up a Jetty server with two servlet mappings.
	 *
	 * @param args unused
	 * @throws Exception if unable to start web server
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(HelloServlet.class, "/hello");
		handler.addServletWithMapping(TodayServer.TodayServlet.class, "/today");

		server.setHandler(handler);
		server.start();

		log.info("Server: {} with {} threads", server.getState(), server.getThreadPool().getThreads());
		server.join();
	}

	/**
	 * Returns the day of the week for today's date.
	 *
	 * @return the day of the week for today's date
	 */
	public static String dayOfWeek() {
		return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	}

	/**
	 * Reads user name passed via a GET query string and uses that input to say
	 * hello to the user. Demonstrates XSS attacks caused by this approach. THIS
	 * IS AN EXAMPLE OF WHAT **NOT** TO DO!
	 */
	public static class HelloServlet extends HttpServlet {
		/** Class version for serialization, in [YEAR][TERM] format (unused). */
		private static final long serialVersionUID = 202301;

		/** The title to use for this webpage. */
		private static final String TITLE = "Hello";

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
					<p>Hello, %2$s!</p>
					<p>Thank you for visiting on this fine %3$s.</p>
					</body>
					</html>
					""";

			String name = request.getParameter("name");
			name = name == null ? "anonymous" : name;

			/*
			 * This can lead to a cross-site scripting attack! NEVER directly use
			 * input from the user in your webpage.
			 *
			 * Some browsers are smart enough to not load Javascript found within the
			 * URL request... so these examples may no longer work.
			 */

			// http://localhost:8080/hello?name=Sophie
			// http://localhost:8080/hello?name=<marquee>Sophie</marquee>
			// http://localhost:8080/hello?name=<script>window.open("http://www.usfca.edu/");</script>
			// http://localhost:8080/hello?name=<script>alert("Mwahaha");</script>

			PrintWriter out = response.getWriter();
			out.printf(html, TITLE, name, dayOfWeek());

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}

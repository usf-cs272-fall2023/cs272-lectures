package edu.usfca.cs272;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * A simple example of using Jetty and servlets to use an HTML form.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class ReverseServer {
	/** The hard-coded port to run this server. */
	public static final int PORT = 8080;

	/**
	 * The logger to use (Jetty is configured via the pom.xml to use Log4j2)
	 */
	public static Logger log = LogManager.getLogger();

	/**
	 * Sets up a Jetty server configured to use the servlets defined in this class.
	 *
	 * @param args unused
	 * @throws Exception if unable to start web server
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(ReverseGetServlet.class, "/get_reverse");
		handler.addServletWithMapping(ReversePostServlet.class, "/post_reverse");

		server.setHandler(handler);
		server.start();

		log.info("Server: {} with {} threads", server.getState(), server.getThreadPool().getThreads());
		server.join();
	}

	/**
	 * Outputs and responds to HTML form.
	 */
	public static class ReverseGetServlet extends HttpServlet {
		/** Class version for serialization, in [YEAR][TERM] format (unused). */
		private static final long serialVersionUID = 202301;

		/** The title to use for this webpage. */
		private static final String TITLE = "Reverse";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info(request);

			String html = """
					<!DOCTYPE html>
					<html lang="en">

					<head>
					  <meta charset="utf-8">
					  <title>%1$s</title>
					</head>

					<body>
					<h1>%1$s</h1>

					<form method="get" action="/get_reverse">
					  <p>
					    <input type="text" name="word" size="50"></input>
					  </p>

					  <p>
					    <button>Reverse</button>
					  </p>
					</form>

					<pre>
					%2$s
					</pre>

					</body>
					</html>
					""";

			String reversed = request.getParameter("word");
			log.info("{}", reversed);

			if (reversed == null || reversed.isBlank()) {
				reversed = "";
			}
			else {
				reversed = new StringBuilder(reversed).reverse().toString();
				reversed = StringEscapeUtils.escapeHtml4(reversed);
			}

			// wolf, stressed, pool
			// <b>hiii</b>
			// >b/<iiih>b<

			PrintWriter out = response.getWriter();
			out.printf(html, TITLE, reversed, Thread.currentThread().getName());

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
		}

		/*
		 * Pros: Nice all-in-one method, good for simple forms, easy to re-display
		 * the form.
		 *
		 * Cons: Method has to both handle generating HTML and processing data. Can
		 * end up too complex for more complicated forms. Parameters in URL.
		 */
	}

	/**
	 * Outputs and responds to HTML form.
	 */
	public static class ReversePostServlet extends HttpServlet {
		/** Class version for serialization, in [YEAR][TERM] format (unused). */
		private static final long serialVersionUID = 202301;

		/** The title to use for this webpage. */
		private static final String TITLE = "Reverse";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info(request);

			String html = """
					<!DOCTYPE html>
					<html lang="en">

					<head>
					  <meta charset="utf-8">
					  <title>%1$s</title>
					</head>

					<body>
					<h1>%1$s</h1>

					<form method="post" action="/post_reverse">
					  <p>
					    <input type="text" name="word" size="50"></input>
					  </p>

					  <p>
					    <button>Reverse</button>
					  </p>
					</form>

					</body>
					</html>
					""";

			PrintWriter out = response.getWriter();
			out.printf(html, TITLE, Thread.currentThread().getName());

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
		}

		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info(request);

			String html = """
					<!DOCTYPE html>
					<html lang="en">

					<head>
					  <meta charset="utf-8">
					  <title>%1$s</title>
					</head>

					<body>
					<h1>%1$s</h1>

					<pre>
					%2$s
					</pre>

					<p>(<a href="/post_reverse">back to form</a>)</p>

					</body>
					</html>
					""";

			String reversed = request.getParameter("word");
			log.info("{}", reversed);

			if (reversed == null || reversed.isBlank()) {
				reversed = "";
			}
			else {
				reversed = new StringBuilder(reversed).reverse().toString();
				reversed = StringEscapeUtils.escapeHtml4(reversed);
			}

			PrintWriter out = response.getWriter();
			out.printf(html, TITLE, reversed, Thread.currentThread().getName());

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
		}

		/*
		 * Pros: Possible to separate logic generating HTML and processing data for
		 * complex cases. No parameters in URL.
		 *
		 * Cons: For this simple example, duplicate display logic. Not re-displaying
		 * form after generating result.
		 */
	}
}

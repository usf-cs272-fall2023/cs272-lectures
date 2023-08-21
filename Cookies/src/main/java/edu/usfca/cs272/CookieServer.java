package edu.usfca.cs272;

import org.apache.commons.text.StringEscapeUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * Demonstrates how to create, use, and clear cookies. Uses
 * {@link StringEscapeUtils} to sanitize data fetched from cookies.
 *
 * <p>
 * Read about the "cookie law" on getting consent to store cookies at:
 * <a href="https://gdpr.eu/cookies/">https://gdpr.eu/cookies</a>
 *
 * <p>
 * When using cookies, keep in mind the following:
 *
 * <ol>
 * <li>Clearly notify users that cookies are being used and what they store.
 *
 * <li>Let users know what happens if they accept or reject those cookies, but
 * do NOT block access if consent is not given.
 *
 * <li>Collect consent BEFORE storing any cookies. Treat the lack of consent as
 * rejecting cookies.
 *
 * <li>Store consent (for documentation purposes) when given.
 *
 * <li>Make it easy to change consent at any time.
 * </ol>
 *
 * @see CookieVisitServlet
 * @see CookieLandingServlet
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class CookieServer {
	/**
	 * Starts a Jetty server configured to use the cookie index and config
	 * servlets.
	 *
	 * @param args unused
	 * @throws Exception if unable to start or run server
	 */
	public static void main(String[] args) throws Exception {
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(CookieLandingServlet.class, "/");
		handler.addServletWithMapping(CookieVisitServlet.class, "/visits");

		Server server = new Server(8080);
		server.setHandler(handler);
		server.start();
		server.join();
	}
}

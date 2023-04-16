package edu.usfca.cs272;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Demonstrates how to create, use, and clear cookies.
 *
 * @see CookieVisitServlet
 * @see CookieLandingServlet
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class CookieVisitServlet extends HttpServlet {
	/** Class version for serialization, in [YEAR][TERM] format (unused). */
	private static final long serialVersionUID = 202301;

	/** Logger for this servlet. */
	private static final Logger log = LogManager.getLogger();

	/** The title to use for this webpage. */
	private static final String TITLE = "Cookies!";

	/** Used to fetch whether cookies were approved. */
	private static final String COOKIES_OK = "Cookies";

	/** Used to fetch the visited date from a cookie. */
	private static final String VISIT_DATE = "Visited";

	/** Used to fetch the visited count from a cookie. */
	private static final String VISIT_COUNT = "Count";

	/** Used to format date/time output. */
	private static final String DATE_FORMAT = "hh:mm a 'on' EEEE, MMMM dd yyyy";

	/** Location of the HTML template for this servlet. */
	private static final Path TEMPLATE_PATH = Path.of("src", "main", "resources", "cookie_visits.html");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info(request);

		// skip requests for favicon.ico
		if (request.getRequestURI().endsWith("favicon.ico")) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// create map for html template
		Map<String, String> values = new HashMap<>();

		// configure the page info
		values.put("title", TITLE);
		values.put("url", request.getRequestURL().toString());
		values.put("path", request.getRequestURI());
		values.put("timestamp", LocalDateTime.now().format(ISO_DATE_TIME));
		values.put("thread", Thread.currentThread().getName());

		// configure the form info
		values.put("method", "POST");
		values.put("action", "/visits");

		// put default values for count and visit date/time
		values.put("visit_count", "<em>unknown</em>");
		values.put("visit_date", "<em>unknown</em>");

		// put default values for enabled/disabled buttons
		values.put("enable_on", "");
		values.put("disable_on", "");

		// get all existing cookies
		Map<String, Cookie> cookies = getCookieMap(request);
		values.put("cookies", Integer.toString(cookies.size()));

		// check if we are allowed to use cookies
		if (cookies.containsKey(COOKIES_OK)) {
			log.info("Cookies are enabled.");
			values.put("enable_on", "disabled");

			// set initial count and date
			int count = 1;

			// check for existing count
			if (cookies.containsKey(VISIT_COUNT)) {
				try {
					Cookie visitCount = cookies.get(VISIT_COUNT);
					count = Integer.parseInt(visitCount.getValue()) + 1;
				}
				catch (NumberFormatException e) {
					log.catching(Level.DEBUG, e);
				}
			}

			// update our html template and cookie
			values.put("visit_count", Integer.toString(count));
			response.addCookie(new Cookie(VISIT_COUNT, Integer.toString(count)));

			// set current date and time
			String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));

			// check for existing date
			if (cookies.containsKey(VISIT_DATE)) {
				Cookie visitDate = cookies.get(VISIT_DATE);
				String visited = visitDate.getValue();

				// do not trust values stored in cookies either!
				String decoded = URLDecoder.decode(visited, UTF_8);
				String escaped = StringEscapeUtils.escapeHtml4(decoded);

				// update our html template
				values.put("visit_date", escaped);

				// log differences
				log.info("Original: {}", visited);
				log.info(" Decoded: {}", decoded);
				log.info(" Escaped: {}", escaped);
			}
			else {
				values.put("visit_date", "N/A (first visit)");
			}

			// update visit date, must URL encode (add < > symbols for demo purposes)
			String encodedDate = "<" + URLEncoder.encode(today, UTF_8) + ">";
			response.addCookie(new Cookie(VISIT_DATE, encodedDate));
		}
		else {
			log.info("Cookies are disabled.");
			values.put("disable_on", "disabled");
		}

		// generate html
		String template = Files.readString(TEMPLATE_PATH, UTF_8);
		String html = StringSubstitutor.replace(template, values);

		// setup response
		response.setContentType("text/html");
		response.setStatus(SC_OK);

		// output html
		PrintWriter out = response.getWriter();
		out.write(html);

		// finish up response
		response.flushBuffer();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info(request);

		// check if we are allowed to use cookies
		if (request.getParameter("ok_cookies") != null) {
			log.info("Saving cookie approval...");

			// save that we received permission, save for 1 hour
			Cookie cookie = new Cookie(COOKIES_OK, Boolean.TRUE.toString());
			cookie.setMaxAge(24 * 60 * 60);
			response.addCookie(cookie);
		}

		// check if we are NOT allowed to use cookies
		if (request.getParameter("no_cookies") != null) {
			log.info("Clearing cookies...");
			clearCookies(request, response); // also clears consent from before
		}

		response.sendRedirect(request.getRequestURI());
	}

	/**
	 * Gets the cookies from the HTTP request and maps the cookie name to the cookie
	 * object.
	 *
	 * @param request the HTTP request from web server
	 * @return map from cookie key to cookie value
	 */
	public static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
		HashMap<String, Cookie> map = new HashMap<>();
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				map.put(cookie.getName(), cookie);
			}
		}

		return map;
	}

	/**
	 * Clears all of the cookies included in the HTTP request.
	 *
	 * @param request the HTTP request
	 * @param response the HTTP response
	 */
	public static void clearCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// update cookie values to trigger delete
				cookie.setValue(null);
				cookie.setMaxAge(0);

				// add new cookie to the response
				response.addCookie(cookie);
			}
		}
	}
}

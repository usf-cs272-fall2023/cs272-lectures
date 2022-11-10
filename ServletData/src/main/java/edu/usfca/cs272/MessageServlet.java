package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// More XSS Prevention:
// https://www.owasp.org/index.php/XSS_(Cross_Site_Scripting)_Prevention_Cheat_Sheet

// Apache Comments:
// https://commons.apache.org/proper/commons-lang/download_lang.cgi

/**
 * The servlet class responsible for setting up a simple message board.
 *
 * @see MessageServer
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class MessageServlet extends HttpServlet {
	/** The logger to use for this servlet. */
	private static final Logger log = LogManager.getLogger();

	/** Class version for serialization, in [YEAR][TERM] format (unused). */
	private static final long serialVersionUID = 202240;

	/** Format used for all date output. */
	public static final String longDateFormat = "hh:mm a 'on' EEEE, MMMM dd yyyy";

	/** Used to format dates (already thread-safe). */
	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(longDateFormat);

	/** The title to use for this webpage. */
	public static final String title = "Messages";

	/** Base path with HTML templates. */
	public static final Path base = Path.of("src", "main", "resources", "html");

	/** Template for HTML. **/
	private final String htmlTemplate;

	/** The data structure to use for storing messages. */
	private final LinkedList<Message> messages;

	/**
	 * A record that stores a single message.
	 *
	 * @param body the message
	 * @param name the message author
	 * @param date the message posted date
	 */
	@SuppressWarnings("javadoc") // See: https://bugs.openjdk.org/browse/JDK-8284994
	public static record Message(String body, String name, LocalDateTime date) {
		// Can add additional methods to a record

		/**
		 * Returns values as HTML.
		 * @return values as html
		 */
		public String html() {
			String format = "<p>%s<br><font size=\"-2\">[ posted by %s at %s ]</font></p>";
			return String.format(format, body, name, dateFormatter.format(date));
		}

		/**
		 * Returns values in a handy map.
		 * @return values in a map
		 */
		public Map<String, String> map() {
			return Map.of("name", name, "body", body, "date", dateFormatter.format(date));
		}
	}

	/**
	 * Initializes this message board. Each message board has its own collection
	 * of messages.
	 *
	 * @throws IOException if unable to read template
	 */
	public MessageServlet() throws IOException {
		super();
		messages = new LinkedList<>();
		htmlTemplate = Files.readString(base.resolve("index.html"), UTF_8);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("{} handling: {}", this.hashCode(), request);

		// used to substitute values in our templates
		Map<String, String> values = new HashMap<>();
		values.put("title", title);
		values.put("thread", Thread.currentThread().getName());

		// setup form
		values.put("method", "POST");
		values.put("action", request.getServletPath());

		// compile all of the messages together
		StringBuilder formatted = new StringBuilder();

		// keep in mind multiple threads may access this at once!
		// might be better to use a read/write lock here instead
		synchronized (messages) {
			for (Message message : messages) {
				formatted.append(message.html());
				formatted.append("\n");
			}
		}

		values.put("messages", formatted.toString());

		// generate html from template
		String html = StringSubstitutor.replace(htmlTemplate, values);

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		// output generated html
		PrintWriter out = response.getWriter();
		out.println(html);
		out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.info("{} handling: {}", this.hashCode(), request);

		String name = request.getParameter("name");
		String body = request.getParameter("message");

		name = name == null || name.isBlank() ? "anonymous" : name;
		body = body == null ? "" : body;

		// avoid xss attacks using apache commons text
		name = StringEscapeUtils.escapeHtml4(name);
		body = StringEscapeUtils.escapeHtml4(body);

		// create message
		Message current = new Message(body, name, LocalDateTime.now());
		log.info("Created message: {}", current);

		// keep in mind multiple threads may access at once
		synchronized (messages) {
			messages.add(current);

			// only keep the latest 5 messages
			while (messages.size() > 5) {
				Message first = messages.poll();
				log.info("Removing message: {}", first);
			}
		}

		response.sendRedirect(request.getServletPath());
	}
}

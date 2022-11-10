package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.usfca.cs272.MessageServlet.Message;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * An alternative implemention of the {@MessageServlet} class but using the
 * Bulma CSS framework.
 *
 * @see MessageServlet
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class BulmaMessageServlet extends HttpServlet {
	/** Class version for serialization, in [YEAR][TERM] format (unused). */
	private static final long serialVersionUID = 202240;

	/** The title to use for this webpage. */
	private static final String title = "Fancy Messages";

	/** The logger to use for this servlet. */
	private static final Logger log = LogManager.getLogger();

	/** Base path with HTML templates. */
	private static final Path base = MessageServlet.base;

	/** The data structure to use for storing messages. */
	private final LinkedList<MessageServlet.Message> messages;

	/** Template for starting HTML (including <head> tag). **/
	private final String headTemplate;

	/** Template for ending HTML (including <foot> tag). **/
	private final String footTemplate;

	/** Template for individual message HTML. **/
	private final String textTemplate;

	/**
	 * Initializes this message board. Each message board has its own collection
	 * of messages.
	 *
	 * @throws IOException if unable to read templates
	 */
	public BulmaMessageServlet() throws IOException {
		super();
		messages = new LinkedList<>();

		// load templates
		headTemplate = Files.readString(base.resolve("bulma-head.html"), UTF_8);
		footTemplate = Files.readString(base.resolve("bulma-foot.html"), UTF_8);
		textTemplate = Files.readString(base.resolve("bulma-text.html"), UTF_8);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("{} handling: {}", this.hashCode(), request);

		// used to substitute values in our templates
		Map<String, String> values = new HashMap<>();
		values.put("title", title);
		values.put("thread", Thread.currentThread().getName());
		values.put("updated", MessageServlet.dateFormatter.format(LocalDateTime.now()));

		// setup form
		values.put("method", "POST");
		values.put("action", request.getServletPath());

		// generate html from template
		StringSubstitutor replacer = new StringSubstitutor(values);
		String head = replacer.replace(headTemplate);
		String foot = replacer.replace(footTemplate);

		// prepare response
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		// output generated html
		PrintWriter out = response.getWriter();
		out.println(head);

		// could be accessed by multiple threads
		synchronized (messages) {
			if (messages.isEmpty()) {
				out.printf("    <p>No messages.</p>%n");
			}
			else {
				for (MessageServlet.Message message : messages) {
					String html = StringSubstitutor.replace(textTemplate, message.map());
					out.println(html);
				}
			}
		}

		out.println(foot);
		out.flush();
	}

	// same logic as message servlet
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.info("{} handling: {}", this.hashCode(), request);

		String name = request.getParameter("name");
		String body = request.getParameter("message");

		name = name == null || name.isBlank() ? "anonymous" : name;
		body = body == null ? "" : body;

		name = StringEscapeUtils.escapeHtml4(name);
		body = StringEscapeUtils.escapeHtml4(body);

		Message current = new Message(body, name, LocalDateTime.now());
		log.info("Created message: {}", current);

		synchronized (messages) {
			messages.add(current);

			while (messages.size() > 5) {
				Message first = messages.poll();
				log.info("Removing message: {}", first);
			}
		}

		response.sendRedirect(request.getServletPath());
	}
}

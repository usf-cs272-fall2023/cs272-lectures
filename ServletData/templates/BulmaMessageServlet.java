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

public class BulmaMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 202301;
	private static final String title = "Fancy Messages";
	private static final Logger log = LogManager.getLogger();
	private static final Path base = MessageServlet.base;

	private final LinkedList<MessageServlet.Message> messages;
	private final String headTemplate;
	private final String footTemplate;
	private final String textTemplate;

	public BulmaMessageServlet() throws IOException {
		super();
		messages = new LinkedList<>();
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
		values.put("method", "POST");
		values.put("action", request.getServletPath());

		StringSubstitutor replacer = new StringSubstitutor(values);
		String head = replacer.replace(headTemplate);
		String foot = replacer.replace(footTemplate);

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		PrintWriter out = response.getWriter();
		out.println(head);

		// TODO

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

		Message current = null; // TODO
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

package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class MessageServlet extends HttpServlet {
	private static final Logger log = LogManager.getLogger();
	private static final long serialVersionUID = 202301;
	public static final String longDateFormat = "hh:mm a 'on' EEEE, MMMM dd yyyy";
	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(longDateFormat);
	public static final String title = "Messages";
	public static final Path base = Path.of("src", "main", "resources", "html");

	private final String htmlTemplate;
	private final LinkedList<Message> messages;

	public static record Message(/* TODO */) {
		// TODO
	}

	public MessageServlet() throws IOException {
		super();
		messages = new LinkedList<>();
		htmlTemplate = Files.readString(base.resolve("index.html"), UTF_8);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("{} handling: {}", this.hashCode(), request);

		String html = htmlTemplate;// TODO

		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		PrintWriter out = response.getWriter();
		out.println(html);
		out.flush();
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.info("{} handling: {}", this.hashCode(), request);

		String name = "anonymous";
		String body = "hello world";

		// TODO
	}
}

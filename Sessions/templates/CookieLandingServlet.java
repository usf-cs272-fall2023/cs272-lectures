package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieLandingServlet extends HttpServlet {
	public static final long serialVersionUID = 202210;
	public static final Logger log = LogManager.getLogger();
	public static final String TITLE = "Cookies!";
	public static final Path TEMPLATE_PATH = Path.of("src", "main", "resources", "cookie_landing.html");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info(request);

		Map<String, String> values = new HashMap<>();

		values.put("title", TITLE);
		values.put("url", request.getRequestURL().toString());
		values.put("path", request.getRequestURI());
		values.put("timestamp", LocalDateTime.now().format(ISO_DATE_TIME));
		values.put("thread", Thread.currentThread().getName());

		// TODO

		String template = Files.readString(TEMPLATE_PATH, UTF_8);
		response.getWriter().write(template);
		response.flushBuffer();
	}
}

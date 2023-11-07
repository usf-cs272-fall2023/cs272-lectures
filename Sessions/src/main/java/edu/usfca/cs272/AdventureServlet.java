package edu.usfca.cs272;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Demonstrates how to use session tracking and enum types to create a simple
 * adventure game.
 *
 * @see AdventureServer
 * @see AdventureServlet
 * @see AdventureRoom
 * @see Direction
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class AdventureServlet extends HttpServlet {
	/** Class version for serialization, in [YEAR][TERM] format (unused). */
	private static final long serialVersionUID = 202308;

	/** The logger for this servlet */
	private static Logger log = LogManager.getLogger();

	/** The title to use for this webpage. */
	private static final String TITLE = "Adventure!";

	/** Location of the HTML template for this servlet. */
	private static final Path TEMPLATE_PATH = Path.of("src", "main", "resources", "adventure.html");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		// tracks current game state
		AdventureRoom room = null;
		Direction direction = null;

		// replacement values for template
		Map<String, String> values = new HashMap<>();

		// configure the page info
		values.put("title", TITLE);
		values.put("url", request.getRequestURL().toString());
		values.put("path", request.getRequestURI());
		values.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
		values.put("thread", Thread.currentThread().getName());

		// try to get current game state
		try {
			room = (AdventureRoom) session.getAttribute("room");
			direction = Direction.valueOf(request.getParameter("direction"));
		}
		catch (Exception e) {
			log.debug(e);
		}

		// make sure values are valid
		if (room == null || direction == null) {
			log.info("Starting new game for session {}", session.getId());
			room = AdventureRoom.START_ROOM;
			direction = Direction.EAST;
		}
		else {
			int old = room.ordinal();
			room = room.moveRoom(direction);
			log.info("Session {} moved from {} to {}", session.getId(), old, room.ordinal());
		}

		// add room information to template values
		values.put("room", Integer.toString(room.ordinal()));
		values.put("message", room.toString());
		values.put("direction", direction.name());

		values.put("action", response.encodeURL("/"));
		values.put("session", session.getId());

		// save the updated room value in the session
		session.setAttribute("room", room);

		if (room.done()) {
			// game won or lost
			log.info("Game finished for session {}", session.getId());
			session.setAttribute("room", AdventureRoom.START_ROOM);
			session.invalidate();

			// make all of the direction buttons disabled
			values.put("west", "disabled");
			values.put("east", "disabled");
			values.put("north", "disabled");
			values.put("south", "disabled");
		}
		else {
			// calculate which buttons to disable
			values.put("west", room.canMove(Direction.WEST) ? "" : "disabled");
			values.put("east", room.canMove(Direction.EAST) ? "" : "disabled");
			values.put("north", room.canMove(Direction.NORTH) ? "" : "disabled");
			values.put("south", room.canMove(Direction.SOUTH) ? "" : "disabled");
		}

		// setup template using commons-text
		String template = Files.readString(TEMPLATE_PATH, UTF_8);

		// generate html from template
		StringSubstitutor replacer = new StringSubstitutor(values);
		String html = replacer.replace(template);

		// prepare response
		response.setContentType("text/html");
		response.setStatus(SC_OK);

		// write resulting html to response
		PrintWriter writer = response.getWriter();
		writer.write(html);

		// finish up response
		response.flushBuffer();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

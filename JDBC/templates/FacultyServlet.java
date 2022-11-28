package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FacultyServlet extends HttpServlet {
	private static final long serialVersionUID = 202240;
	private static final Logger log = LogManager.getLogger();

	private final DatabaseConnector connector;
	private final String sqlSelect;
	private final String htmlHeader;
	private final String htmlRow;
	private final String htmlFooter;
	private static final Set<String> COLUMNS = Set.of("last", "first", "email", "twitter", "courses");

	public FacultyServlet(DatabaseConnector connector) throws IOException {
		this.connector = connector;

		Path sql = Path.of("src", "main", "resources", "sql");
		Path html = Path.of("src", "main", "resources", "html");

		sqlSelect = Files.readString(sql.resolve("SELECT.sql"), UTF_8);
		htmlHeader = Files.readString(html.resolve("header.html"), UTF_8);
		htmlRow = Files.readString(html.resolve("row.html"), UTF_8);
		htmlFooter = Files.readString(html.resolve("footer.html"), UTF_8);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info(request.getQueryString());

		String sort = getColumn(request, "sort", "last");
		boolean asc = !isEqual(request, "asc", "false");
		String query = request.getParameter("query");
		String field = getColumn(request, "field", "last");
		boolean onTwitter = isEqual(request, "twitter", "on");
		boolean hasFilter = query != null && !query.isBlank();

		StringBuilder sql = new StringBuilder(sqlSelect);

		if (onTwitter) {
			sql.append(System.lineSeparator());
			// TODO twitter filter
		}

		if (hasFilter && !field.equals("courses")) {
			sql.append(System.lineSeparator());
			// TODO non-course filters
		}

		sql.append(System.lineSeparator());
		// TODO grouping

		if (hasFilter && field.equals("courses")) {
			sql.append(System.lineSeparator());
			// TODO course filters
		}

		sql.append(System.lineSeparator());
		// TODO sorting

		sql.append(";");
		log.info("SQL: {}", sql);

		StringBuffer action = new StringBuffer();

		// TODO form action
//		action.append("&twitter=on");
//		action.append("&field=");
//		action.append(field);
//
//		String encoded = URLEncoder.encode(query, UTF_8);
//		action.append("&query=");
//		action.append(encoded);

		PrintWriter out = response.getWriter();
		out.println(getHeader(action.toString(), sort, asc));

		try (
				Connection db = connector.getConnection();
				PreparedStatement statement = db.prepareStatement(sql.toString());
		) {
			// TODO add filter

			try (ResultSet results = null) { // TODO get results
				while (results.next()) {
					Map<String, String> values = new HashMap<>();
					values.put("name", escape(results, "name"));
					values.put("email", escape(results, "email"));
					values.put("courses", "&nbsp;");
					values.put("twitter", "&nbsp;");

					String courses = escape(results, "courses");
					String twitter = escape(results, "twitter");

					if (courses != null && !courses.isBlank()) {
						values.put("courses", courses);
					}

					if (twitter != null && !twitter.isBlank()) {
						String link = String.format("<a href=\"http://twitter.com/%1$s\">@%1$s</a>", twitter);
						values.put("twitter", link);
					}

					out.println(StringSubstitutor.replace(htmlRow, values));
					out.println();
				}
			}
		}
		catch (SQLException e) {
			log.warn(e);
		}

		out.println(getFooter(sort, asc));
		response.setStatus(HttpServletResponse.SC_OK);
		response.flushBuffer();
	}

	private String getHeader(String filter, String sort, boolean asc) {
		Map<String, String> values = new HashMap<>();
		values.put("last", "true");
		values.put("email", "true");
		values.put("twitter", "true");
		values.put("courses", "true");
		values.put("filter", filter);
		values.put(sort, Boolean.toString(!asc));
		return StringSubstitutor.replace(htmlHeader, values);
	}

	private String getFooter(String sort, boolean asc) {
		Map<String, String> values = new HashMap<>();
		values.put("sort", sort);
		values.put("asc", Boolean.toString(asc));
		values.put("thread", Thread.currentThread().getName());
		values.put("date", getLongDate());
		return StringSubstitutor.replace(htmlFooter, values);
	}

	public static String getColumn(HttpServletRequest request, String key, String value) {
		String found = request.getParameter(key);
		return found != null && COLUMNS.contains(found) ? found : value;
	}

	public static boolean isEqual(HttpServletRequest request, String key, String value) {
		String found = request.getParameter(key);
		return found != null && found.equalsIgnoreCase(value);
	}

	public static String escape(ResultSet results, String column) throws SQLException {
		return StringEscapeUtils.escapeHtml4(results.getString(column));
	}

	public static String getLongDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return today.format(formatter);
	}
}

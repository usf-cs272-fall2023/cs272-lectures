package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLEncoder;
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

/**
 * Demonstrates how to interact with a database server using JDBC and Jetty.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class FacultyServlet extends HttpServlet {
	/**
	 * Version ID for serialization.
	 *
	 * @see Serializable
	 */
	private static final long serialVersionUID = 202240;

	/** Logger */
	private static final Logger log = LogManager.getLogger();

	/** Database connector */
	private final DatabaseConnector connector;

	/** Template for SQL statements. */
	private final String sqlSelect;

	/** Template for HTML header. */
	private final String htmlHeader;

	/** Template for HTML table row. */
	private final String htmlRow;

	/** Template for HTML footer. */
	private final String htmlFooter;

	/** Allowlist of column names; used to prevent XSS/SQL injection issues. */
	private static final Set<String> COLUMNS =
			Set.of("last", "first", "email", "twitter", "courses");

	/**
	 * Initializes this servlet. Requires an already established database
	 * connector. Will fail to initialize if template files are not found.
	 *
	 * @param connector the database connector to use (one per server)
	 * @throws IOException if unable to connect
	 */
	public FacultyServlet(DatabaseConnector connector) throws IOException {
		this.connector = connector;

		// load all of the SQL and HTML templates.
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

		// (safely) get sort parameters
		String sort = getColumn(request, "sort", "last");
		boolean asc = !isEqual(request, "asc", "false");

		// (safely) get form parameters
		String query = request.getParameter("query");
		String field = getColumn(request, "field", "last");

		boolean onTwitter = isEqual(request, "twitter", "on");
		boolean hasFilter = query != null && !query.isBlank();

		// begin building sql query
		StringBuilder sql = new StringBuilder(sqlSelect);

		// handle filters that must occur BEFORE group by clause
		if (onTwitter) {
			sql.append(System.lineSeparator());
			sql.append("WHERE twitterid IS NOT NULL");
		}

		if (hasFilter && !field.equals("courses")) {
			sql.append(System.lineSeparator());
			sql.append(onTwitter ? "AND " : "WHERE ");
			sql.append(field);
			sql.append(" LIKE ?");
		}

		// add group by clause
		sql.append(System.lineSeparator());
		sql.append("GROUP BY faculty_names.usfid");

		// add filters that must come AFTER group by clause
		if (hasFilter && field.equals("courses")) {
			sql.append(System.lineSeparator());
			sql.append("HAVING ");
			sql.append(field);
			sql.append(" LIKE ?");
		}

		// add sort order to sql query
		sql.append(System.lineSeparator());
		sql.append("ORDER BY ");
		sql.append(sort);
		sql.append(asc ? " ASC" : " DESC");

		// end of sql query
		sql.append(";");
		log.info("SQL: {}", sql);

		// prepare action to use for links (keeps form settings)
		StringBuffer action = new StringBuffer();

		if (onTwitter) {
			action.append("&twitter=on");
		}

		if (hasFilter) {
			try {
				// not safe to just use query (we are putting it in our html after all)
				String encoded = URLEncoder.encode(query, UTF_8);
				action.append("&field=");
				action.append(field);
				action.append("&query=");
				action.append(encoded);
			}
			catch (Exception e) {
				log.warn("Bad query:", query);
			}
		}

		// prepare html output
		PrintWriter out = response.getWriter();
		out.println(getHeader(action.toString(), sort, asc));

		try (
				Connection db = connector.getConnection();
				PreparedStatement statement = db.prepareStatement(sql.toString());
		) {
			// add filter query if necessary
			if (hasFilter) {
				statement.setString(1, query);
			}

			try (ResultSet results = statement.executeQuery()) {
				while (results.next()) {
					// do not assume data stored in database is safe!
					Map<String, String> values = new HashMap<>();
					values.put("name", escape(results, "name"));
					values.put("email", escape(results, "email"));
					values.put("courses", "&nbsp;");
					values.put("twitter", "&nbsp;");

					String courses = escape(results, "courses");

					if (courses != null && !courses.isBlank()) {
						values.put("courses", courses);
					}

					String twitter = escape(results, "twitter");

					if (twitter != null && !twitter.isBlank()) {
						String link = String.format(
								"<a href=\"http://twitter.com/%1$s\">@%1$s</a>", twitter);
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

	/**
	 * Outputs the opening HTML and page header, including the table header row.
	 *
	 * @param filter the form filter to included in the column header links
	 * @param sort the current sort column
	 * @param asc the current sort order
	 * @return html
	 * @throws IOException if IO issue occurs
	 */
	private String getHeader(String filter, String sort, boolean asc) throws IOException {
		Map<String, String> values = new HashMap<>();
		values.put("last", "true");
		values.put("email", "true");
		values.put("twitter", "true");
		values.put("courses", "true");
		values.put("filter", filter);

		// toggle column based on parameters
		values.put(sort, Boolean.toString(!asc));
		return StringSubstitutor.replace(htmlHeader, values);
	}

	/**
	 * Outputs the closing HTML and page footer, including the filter form.
	 *
	 * @param sort the current sort column
	 * @param asc the current sort order
	 * @return html
	 * @throws IOException if IO issue occurs
	 */
	private String getFooter(String sort, boolean asc) throws IOException {
		Map<String, String> values = new HashMap<>();
		values.put("sort", sort);
		values.put("asc", Boolean.toString(asc));
		values.put("thread", Thread.currentThread().getName());
		values.put("date", getLongDate());
		return StringSubstitutor.replace(htmlFooter, values);
	}

	/**
	 * Safely returns a column name for a parameter fetched from the request.
	 *
	 * @param request the http servlet request
	 * @param key the parameter name to get
	 * @param value the default value to return if there are any issues
	 * @return the found column name or the default value if there are any issues
	 */
	public static String getColumn(HttpServletRequest request, String key, String value) {
		String found = request.getParameter(key);
		return found != null && COLUMNS.contains(found) ? found : value;
	}

	/**
	 * Returns whether a parameter equals the provided value.
	 *
	 * @param request the http servlet request
	 * @param key the parameter name to get
	 * @param value the value to test against
	 * @return whether the parameter equals the value (ignoring case)
	 */
	public static boolean isEqual(HttpServletRequest request, String key, String value) {
		String found = request.getParameter(key);
		return found != null && found.equalsIgnoreCase(value);
	}

	/**
	 * Attempts to retrieve data from a result set and escape any special
	 * characters returned. Useful for avoiding XSS attacks.
	 *
	 * @param results the set of results returned from a database
	 * @param column the name of the column to fetch
	 * @return escaped text (or null if result was null)
	 * @throws SQLException if unable to get and escape results
	 */
	public static String escape(ResultSet results, String column) throws SQLException {
		return StringEscapeUtils.escapeHtml4(results.getString(column));
	}

	/**
	 * Returns the current date in long format.
	 *
	 * @return the current date in long format
	 */
	public static String getLongDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return today.format(formatter);
	}
}

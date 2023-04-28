package edu.usfca.cs272;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Set;

public class DatabaseConnector {
	public final String uri;
	private final Properties login;

	public DatabaseConnector() throws FileNotFoundException, IOException {
		this("database.properties");
	}

	public DatabaseConnector(String configPath) throws FileNotFoundException, IOException {
		Properties config = loadConfig(configPath);
		login = new Properties(); // TODO

		String format = "jdbc:mysql://%s/%s";
		uri = null; // TODO
	}

	public static Properties loadConfig(String configPath) throws FileNotFoundException, IOException {
		Set<String> required = new HashSet<>();
		required.add("username");
		required.add("password");
		required.add("database");
		required.add("hostname");

		Properties config = null; // TODO

		if (!config.keySet().containsAll(required)) {
			String error = "Must provide the following in properties file: ";
			throw new InvalidPropertiesFormatException(error + required);
		}

		return config;
	}

	public Connection getConnection() throws SQLException {
		return null; // TODO
	}

	public Set<String> getTables(Connection db) throws SQLException {
		Set<String> tables = new HashSet<>();
		// TODO
		return tables;
	}

	public boolean testConnection() {
		boolean okay = false;

		try (Connection db = getConnection();) {
			System.out.println("Executing SHOW TABLES...");
			Set<String> tables = getTables(db);

			if (tables != null) {
				// TODO
			}
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return okay;
	}

	public static void main(String[] args) {
		try {
			String properties = "database.properties";

			if (args.length > 0 && Files.exists(Path.of(args[0]))) {
				properties = args[0];
			}

			DatabaseConnector test = new DatabaseConnector(properties);
			System.out.println("Connecting to " + test.uri);

			if (test.testConnection()) {
				System.out.println("Connection to database established.");
			}
			else {
				System.err.println("Unable to connect properly to database.");
			}
		}
		catch (Exception e) {
			System.err.println("Unable to connect properly to database.");
			System.err.println(e.getMessage());
		}
	}
}

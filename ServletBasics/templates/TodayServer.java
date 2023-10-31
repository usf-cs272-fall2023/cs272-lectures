package edu.usfca.cs272;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TodayServer {
	public static final int PORT = 8080;
	public static Logger log = LogManager.getLogger();

	public static void main(String[] args) throws Exception {
		// TODO Fill in
		// log.info("Server: {} with {} threads", server.getState(), server.getThreadPool().getThreads());
	}

	public static class TodayServlet extends HttpServlet {
		private static final long serialVersionUID = 202301;
		private static final String TITLE = "Today";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info(request.toString());

			String html = """
					<!DOCTYPE html>
					<html lang="en">

					<head>
					  <meta charset="utf-8">
					  TODO
					</head>

					<body>
					TODO
					</body>
					</html>
					""";

			// TODO Fill in
		}
	}

	public static String getDate() {
		String format = "hh:mm:ss a 'on' EEEE, MMMM dd yyyy";
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return today.format(formatter);
	}
}

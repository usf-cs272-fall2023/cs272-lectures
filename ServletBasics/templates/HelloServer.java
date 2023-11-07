package edu.usfca.cs272;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HelloServer {
	public static final int PORT = 8080;
	public static Logger log = LogManager.getLogger();

	public static void main(String[] args) throws Exception {
		// TODO Fill in HelloServer
		// log.info("Server: {} with {} threads", server.getState(), server.getThreadPool().getThreads());
	}

	public static String dayOfWeek() {
		return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	}

	public static class HelloServlet extends HttpServlet {
		private static final long serialVersionUID = 202308;
		private static final String TITLE = "Hello";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info(request.toString());

			String html = """
					<!DOCTYPE html>
					<html lang="en">

					<head>
					  <meta charset="utf-8">
					  <title>%1$s</title>
					</head>

					<body>
					<h1>%1$s</h1>
					TODO
					</body>
					</html>
					""";

			// TODO Fill in
			String name = "anonymous";

			PrintWriter out = response.getWriter();
			out.printf(html, TITLE, name, dayOfWeek());

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}

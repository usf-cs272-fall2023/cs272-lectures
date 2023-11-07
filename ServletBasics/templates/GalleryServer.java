package edu.usfca.cs272;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GalleryServer {
	public static final int PORT = 8080;
	public static Logger log = LogManager.getLogger();

	public static Path base = Path.of("src", "main", "resources");
	public static Path foto = base.resolve("foto");

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		// TODO

		server.start();

		log.info("Server: {} with {} threads", server.getState(), server.getThreadPool().getThreads());
		server.join();
	}

	public static class GalleryServlet extends HttpServlet {
		private static final long serialVersionUID = 202308;
		private static final String TITLE = "Gallery";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.info(request);

			String head = """
					<!DOCTYPE html>
					<html lang="en">

					<head>
					  <meta charset="utf-8">
					  <title>%1$s</title>
					</head>

					<body>
					<h1>%1$s</h1>
					""";

			String loop = """
					<img src="TODO" width="150" height="150"/>
					""";

			String foot = """
					<p>This request was handled by thread %s.</p>
					</body>
					</html>
					""";

			PrintWriter out = response.getWriter();
			out.printf(head, TITLE);

			// TODO Fill in

			out.printf(foot, Thread.currentThread().getName());

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}

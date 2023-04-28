package edu.usfca.cs272;

import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class FacultyServer {
	public static void main(String[] args) throws Exception {
		String properties = "database.properties";

		if (args.length > 0 && Files.exists(Path.of(args[0]))) {
			properties = args[0];
		}

		DatabaseConnector connector = new DatabaseConnector(properties);

		if (!connector.testConnection()) {
			return;
		}

		ContextHandler defaultHandler = new ContextHandler("/favicon.ico");
		defaultHandler.setHandler(new DefaultHandler());

		FacultyServlet servlet = new FacultyServlet(connector);
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(new ServletHolder(servlet), "/");

		HandlerList handlers = new HandlerList();
		handlers.addHandler(defaultHandler);
		handlers.addHandler(servletHandler);

		Server server = new Server(8080);
		server.setHandler(handlers);
		server.start();
		server.join();
	}
}

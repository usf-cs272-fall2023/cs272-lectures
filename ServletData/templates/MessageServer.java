package edu.usfca.cs272;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class MessageServer {
	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);
		ServletHandler handler = new ServletHandler();

		// TODO

		server.setHandler(handler);
		server.start();
		server.join();
	}
}

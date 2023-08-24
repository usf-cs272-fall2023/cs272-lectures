package edu.usfca.cs272;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;

public class SimpleFileServer {
	public static final int PORT = 8080;
	public static Logger log = LogManager.getLogger();

	public static void main(String[] args) throws Exception {
		System.setProperty("org.eclipse.jetty.LEVEL", "DEBUG");

		Server server = new Server(PORT);

		// TODO


		server.start();
		log.info("Server: {} with {} threads", server.getState(), server.getThreadPool().getThreads());

		server.join();
	}
}

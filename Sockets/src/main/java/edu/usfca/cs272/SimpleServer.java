package edu.usfca.cs272;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Demonstrates server-side and client-side sockets.
 *
 * @see SimpleServer
 * @see SimpleClient
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class SimpleServer {
	/** The port for this server. Choose an unused port number. Should usually be configurable! */
	public static final int PORT = 5554;

	/** The end-of-transmission (EOT) text to use to end a transmission. */
	public static final String EOT = "END";

	/** The shutdown text to use to shutdown the server. */
	public static final String EXIT = "EXIT";

	/**
	 * Starts this server on {@value #PORT}.
	 *
	 * @param args unused
	 * @throws IOException if unable to start or run client
	 */
	public static void main(String[] args) throws IOException {
		String line = null;

		try (ServerSocket server = new ServerSocket(PORT);) {
			// keep looping to accept clients
			while (!server.isClosed()) {
				System.out.println("Server: Waiting for connection...");

				try (
						// accept a client socket connection (waits until there is one)
						Socket socket = server.accept();

						// setup reading from the client socket connection
						InputStreamReader input = new InputStreamReader(socket.getInputStream());
						BufferedReader reader = new BufferedReader(input);
				) {
					// while client is connected
					while (!socket.isClosed()) {
						// read line from client socket
						line = reader.readLine();
						System.out.println("Server: " + line);

						// check for shutdown cases
						if (line.equals(EOT)) {
							System.out.println("Server: Closing socket.");
							socket.close();
						}
						else if (line.equals(EXIT)) {
							System.out.println("Server: Shutting down.");
							socket.close(); // close client connection
							server.close(); // close server connection
						}
					}
				}

				System.out.println("Server: Client disconnected.");
			}

			System.out.println("Server: Server disconnected.");
		}
	}
}

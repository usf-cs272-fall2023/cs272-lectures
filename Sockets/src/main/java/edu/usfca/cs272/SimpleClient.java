package edu.usfca.cs272;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Demonstrates server-side and client-side sockets.
 *
 * @see SimpleServer
 * @see SimpleClient
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class SimpleClient {
	/**
	 * Connects to a {@link SimpleServer}.
	 *
	 * @param args unused
	 * @throws IOException if unable to start or run server
	 */
	public static void main(String[] args) throws IOException {
		try (
				// read from console
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

				// setup client socket for writing data to server
				// 127.0.0.1 is localhost is your local computer
				Socket socket = new Socket("127.0.0.1", SimpleServer.PORT);
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
		) {
			System.out.println("Client: Started...");
			String input = null;

			while (!socket.isClosed()) {
				// read line from console
				input = reader.readLine();

				// send to server over socket
				writer.println(input);
				writer.flush();

				// check for shutdown cases
				if (input.equals(SimpleServer.EOT)) {
					System.out.println("Client: Ending client.");
					socket.close();
				}
				else if (input.equals(SimpleServer.EXIT)) {
					System.out.println("Client: Shutting down server.");
					socket.close();
				}
			}
		}
	}
}

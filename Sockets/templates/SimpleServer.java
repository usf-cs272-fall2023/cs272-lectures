package edu.usfca.cs272;

import java.io.IOException;

public class SimpleServer {
	public static final int PORT = 5554;
	public static final String EOT = "END";
	public static final String EXIT = "EXIT";

	public static void main(String[] args) throws IOException {
		String line = null;

		System.out.println("Server: Waiting for connection...");
		System.out.println("Server: Closing socket.");
		System.out.println("Server: Shutting down.");
		System.out.println("Server: Client disconnected.");
		System.out.println("Server: Server disconnected.");
	}
}

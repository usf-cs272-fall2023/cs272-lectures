package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.Path;

@SuppressWarnings("unused")
public class DirectoryStreamDemo {
	private static final String FORMAT = "%s (%d bytes)%n";

	private static void traverseDirectory(Path directory) throws IOException {
		// TODO Implement traverseDirectory method
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	public static void printListing(Path start) throws IOException {
		// TODO Implement printListing method
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	public static void main(String[] args) throws IOException {
		Path path = Path.of(".").normalize();
		printListing(path);
	}
}

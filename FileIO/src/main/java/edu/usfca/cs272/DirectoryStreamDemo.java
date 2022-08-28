package edu.usfca.cs272;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This class demonstrates how to use a {@link DirectoryStream} to create a
 * recursive file listing.
 *
 * @see java.nio.file.Path
 * @see java.nio.file.Files
 * @see java.nio.file.DirectoryStream
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class DirectoryStreamDemo {
	/**
	 * A format string for outputting a path and its size on a single line.
	 */
	private static final String FORMAT = "%s (%d bytes)%n";

	/**
	 * Traverses through the directory and its subdirectories, outputting all
	 * paths to the console. For files, also includes the file size in bytes.
	 *
	 * @param directory the directory to traverse
	 * @throws IOException if an I/O error occurs
	 */
	private static void traverseDirectory(Path directory) throws IOException {
		/*
		 * The try-with-resources block makes sure we close the directory stream
		 * when done, to make sure there aren't any issues later when accessing this
		 * directory.
		 *
		 * Note, however, we are still not catching any exceptions. This type of try
		 * block does not have to be accompanied with a catch block. (You should,
		 * however, do something about the exception.)
		 */
		try (DirectoryStream<Path> listing = Files.newDirectoryStream(directory)) {
			// use an enhanced-for or for-each loop for efficiency and simplicity
			for (Path path : listing) {
//				if (Files.isDirectory(path)) {
//					System.out.println(path.toString() + "/");
//					traverseDirectory(path);
//				}
//				else {
//					System.out.printf(FORMAT, path.toString(), Files.size(path));
//				}

				// note the duplicated logic above with traverse()!
				// avoid the duplicated code by just calling the traverse() method
				// (requires designing it just right to make this possible)
				printListing(path);
			}
		}
	}

	/**
	 * Traverses through the directory and its subdirectories, outputting all
	 * paths to the console. For files, also includes the file size in bytes.
	 *
	 * @param start the initial path to traverse
	 * @throws IOException if an I/O error occurs
	 */
	public static void printListing(Path start) throws IOException {
		// use the Files class to get information about a path
		if (Files.isDirectory(start)) {
			// output trailing slash to indicate directory
			System.out.println(start.toString() + "/");

			// start directory traversal
			traverseDirectory(start);
		}
		else {
			// output the file path and file size in bytes
			System.out.printf(FORMAT, start.toString(), Files.size(start));
		}
	}

	/**
	 * Recursively traverses the current directory and prints the file listing.
	 *
	 * @param args unused
	 * @throws IOException if an I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		Path path = Path.of(".").normalize();
		printListing(path);
	}
}

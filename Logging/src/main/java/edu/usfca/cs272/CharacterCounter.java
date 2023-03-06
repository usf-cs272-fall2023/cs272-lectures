package edu.usfca.cs272;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class demonstrates very basic debugging approaches, including using a
 * debugger, assertions, println statements, a {@link Debug} class, and logging
 * using the log4j2 package.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class CharacterCounter {
	/**
	 * Size used for the character buffer. Set to a small size to force our code to
	 * loop to demonstrate debugging techniques.
	 */
	private static int SIZE = 1000;

	/**
	 * This method does not return the correct value. We can use a debugger to try
	 * and figure out what is going on, but it will be difficult to use a debugger
	 * when we get to multithreading. We also have a silent exception, so there is
	 * no indication something went wrong!
	 *
	 * @param file the file to open
	 * @return number of characters
	 */
	public static int countNoDebug(Path file) {
		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			while (count >= 0) {
				count = reader.read(buffer);
				total += count;
			}
		}
		catch (IOException e) {

		}

		return total;
	}

	/**
	 * This method adds assertions to the previous approach. This requires the -ea
	 * flag (enable assertions flag) to be turned on to work. While this helps some,
	 * we need more information.
	 *
	 * Note: The bug is still present in this code.
	 *
	 * @param file the file to open
	 * @return number of characters
	 */
	public static int countAssertions(Path file) {
		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			while (count >= 0) {
				count = reader.read(buffer);
				total += count;
			}

			// post-condition assert statement
			assert count < 0;
		}
		catch (IOException e) {

		}

		// post-condition assert statement
		assert total >= 0;
		return total;
	}

	/**
	 * We can add print statements to help with debugging, but once we figure out
	 * the bug, we have to comment most of these statements out!
	 *
	 * Note: The bug is still present in this code.
	 *
	 * @param file the file to open
	 * @return number of characters
	 */
	public static int countWithPrintln(Path file) {
		System.out.println("Start: " + file);

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			System.out.println(" Open: " + file);

			while (count >= 0) {
				count = reader.read(buffer);
				System.out.println(" Read: " + count);

				total += count;
				System.out.println("Total: " + total);
			}

			assert count < 0;
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}

		System.out.println(" Done: " + file + "\n");
		assert total >= 0;
		return total;
	}

	/**
	 * This method replaces the println statements with those from a Debug class. We
	 * can now turn on or off all of these messages, but that is the only level of
	 * configuration we have.
	 *
	 * Note: The bug is still present in this code.
	 *
	 * @param file the file to open
	 * @return number of characters
	 */
	public static int countWithDebug(Path file) {
		Debug.println("Start: " + file);

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			Debug.println(" Open: " + file);

			while (count >= 0) {
				count = reader.read(buffer);
				Debug.println(" Read: " + count);

				total += count;
				Debug.println("Total: " + total);
			}

			assert count < 0;
		}
		catch (IOException e) {
			Debug.println(e.toString());
		}

		Debug.println(" Done: " + file + "\n");
		assert total >= 0;
		return total;
	}

	/**
	 * Used by {@link #countWithLogging(Path)} to demonstrate the flexibility of
	 * logging with log4j2.
	 */
	private static Logger logger = LogManager.getLogger(CharacterCounter.class);

	/**
	 * Demonstrates how we can use Log4j2 instead of a Debug class to help with
	 * debugging our method. Assumes the log4j2.xml is in the classpath!
	 *
	 * Note: The bug is still present in this code.
	 *
	 * @param file the file to open
	 * @return number of characters
	 */
	public static int countWithLogging(Path file) {
		logger.debug("Start: {}", file);

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			logger.trace(" Open: {}", file);

			while (count >= 0) {
				count = reader.read(buffer);
				logger.debug(" Read: {}", count);

				total += count;
				logger.debug("Total: {}", total);
			}

			assert count < 0;
		}
		catch (IOException e) {
			logger.warn("Unable to count characters in: {}", file);
			logger.debug("Unable to count characters.", e);
		}

		logger.debug("Done: {}\n", file);
		assert total >= 0;
		return total;
	}

	/**
	 * Final version of this class with the bug fixed but the logging messages
	 * remain in place. We added some assert statements and modified the code a bit
	 * as well. We can disable these messages entirely in log4j2.xml.
	 *
	 * @param file the file to open
	 * @return number of characters
	 */
	public static int countCharacters(Path file) {
		logger.debug("Start: {}", file);

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);) {
			logger.trace(" Open: {}", file);

			while (count >= 0) {
				total += count;
				logger.debug("Total: {}", total);

				count = reader.read(buffer);
				logger.debug(" Read: {}", count);
			}

			assert count < 0;
		}
		catch (IOException e) {
			logger.warn("Unable to count characters in: {}", file);
			logger.debug("Unable to count characters.", e);
		}

		logger.debug("Done: {}\n", file);
		assert total >= 0;
		return total;
	}

	/**
	 * Tests out two paths, one that is not a file, and one that is a file. To get
	 * how many characters are expected to be returned for this file, look at its
	 * properties in Eclipse.
	 *
	 * @param args unused
	 * @throws IOException if unable to read file
	 */
	public static void main(String[] args) throws IOException {
		// demonstrate problem for files that exist
		Path readme = Path.of("README.md");

		System.out.println("    Path: " + readme.toString());
		System.out.println("  Actual: " + countNoDebug(readme));
		System.out.println("Expected: " + Files.size(readme));
		System.out.println();

		// demonstrate problem for files that do not exist
		Path nowhere = Path.of("nowhere");

		System.out.println("    Path: " + nowhere.toString());
		System.out.println("  Actual: " + countNoDebug(nowhere));

		try {
			System.out.print("Expected: ");
			System.out.print(Files.size(nowhere));
		}
		catch (IOException e) {
			System.err.print(e.toString());
			System.out.println("\n");
		}

		// first check if assertions are enabled
		try {
			System.out.println("Check Assertions:");
			assert false : "hello world";
		}
		catch (AssertionError e) {
			System.out.println(e.toString());
		}
		finally {
			System.out.println();
		}

		// try to debug first problem (off by one)
		System.out.println("With Assertions:");
		System.out.println(countAssertions(readme));
		System.out.println();

		System.out.println("With Println Statements:");
		System.out.println(countWithPrintln(readme));
		System.out.println();

		Debug.on = true;
		System.out.println("With Debug Statements:");
		System.out.println(countWithDebug(readme));
		System.out.println();

		System.out.println("With Logging:");
		System.out.println(countWithLogging(readme));
		System.out.println();

		System.out.println("Final Version:");
		System.out.println(countCharacters(readme));
		System.out.println();

		// demonstrate differences in exception handling
		countAssertions(nowhere);
		countWithPrintln(nowhere);
		countWithLogging(nowhere);
		countCharacters(nowhere);
	}
}

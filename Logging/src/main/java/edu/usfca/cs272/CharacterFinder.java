package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides different implementations to count the number of times a specified
 * character appears in a file.
 *
 * @see CharacterFinder
 * @see CharacterCompare
 * @see CharacterBenchmark
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class CharacterFinder {
	/** Logger used for all output generated in this class. */
	private static final Logger log = LogManager.getLogger();

	/**
	 * Takes a line-by-line approach to counting the number of times a specific
	 * character appears in a file.
	 *
	 * @param file file path to open and read
	 * @param character character to search for in text
	 * @param ignoreCase turns on or off case-insensitive search
	 * @return number of times the character was found
	 * @throws IOException if unable to read file
	 */
	public static int findCharacter(Path file, char character, boolean ignoreCase) throws IOException {
		int count = 0;

		// note that we are outputting log messages
		log.debug("Searching for {} in {} (ignore case: {}).", character, file, ignoreCase);

		// note the try-with-resources block **without** a catch statement
		// note the use of Files.newBufferedReader() to create the reader
		try (BufferedReader reader = Files.newBufferedReader(file, UTF_8);) {
			String line;

			// read one line at a time until hit end of file (null)
			while ((line = reader.readLine()) != null) {

				// convert to lowercase if ignoring case
				if (ignoreCase) {
					line = line.toLowerCase();
				}

				// loop through every character in line
				for (char current : line.toCharArray()) {
					if (current == character) {
						count++;
					}
				}
			}
		}

		log.debug("Found {} instances of {} in {}.", count, character, file);
		return count;
	}

	/**
	 * Performs some sanity-checks of the
	 * {@link #findCharacter(Path, char, boolean)} method.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		Path sherlock = Path.of("src", "main", "resources", "pg1661.txt");

		char lower = 'c';
		char upper = Character.toUpperCase(lower);

		try {
			int countLowers = findCharacter(sherlock, lower, false);
			int countUppers = findCharacter(sherlock, upper, false);
			int countIgnore = findCharacter(sherlock, lower, true);

			assert countLowers + countUppers == countIgnore;

			// console output only if info messages enabled
			log.info("Found {} instances of {} character.", countLowers, lower);
			log.info("Found {} instances of {} character.", countUppers, upper);
			log.info("Found {} instances of {} character (ignore case).", countIgnore, lower);

			// demonstrate exception
			findCharacter(Path.of("nowhere"), lower, true);
		}
		catch (IOException e) {
			// note the console-friendly warning
			log.warn("Unable to count characters: {}", e.getMessage());

			// note the debug-friendly stack trace
			log.catching(Level.DEBUG, e);
		}
	}
}

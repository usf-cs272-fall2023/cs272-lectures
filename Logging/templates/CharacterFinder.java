package edu.usfca.cs272;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CharacterFinder {
	private static final Logger log = LogManager.getLogger();

	public static int findCharacter(Path file, char character, boolean ignoreCase) throws IOException {
		int count = 0;

		log.debug("Searching for {} in {} (ignore case: {}).", character, file, ignoreCase);

		try (BufferedReader reader = Files.newBufferedReader(file, UTF_8);) {
			String line;

			while ((line = reader.readLine()) != null) {
				// TODO
			}
		}

		log.debug("Found {} instances of {} in {}.", count, character, file);
		return count;
	}

	public static void main(String[] args) {
		Path sherlock = Path.of("src", "main", "resources", "pg1661.txt");

		char lower = 'c';
		char upper = Character.toUpperCase(lower);

		try {
			int countLowers = findCharacter(sherlock, lower, false);
			int countUppers = findCharacter(sherlock, upper, false);
			int countIgnore = findCharacter(sherlock, lower, true);

			// TODO

			log.info("Found {} instances of {} character.", countLowers, lower);
			log.info("Found {} instances of {} character.", countUppers, upper);
			log.info("Found {} instances of {} character (ignore case).", countIgnore, lower);

			// TODO
			// findCharacter(Path.of("nowhere"), lower, true);
		}
		catch (IOException e) {
			log.warn("Unable to count characters in {}.", sherlock);
			log.catching(Level.DEBUG, e);
		}
	}
}

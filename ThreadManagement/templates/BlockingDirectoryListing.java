package edu.usfca.cs272;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockingDirectoryListing {
	private static final Logger log = LogManager.getLogger();

	public static Set<Path> list(Path path) throws InterruptedException {
		Set<Path> paths = new HashSet<>();

		if (Files.exists(path)) {
			paths.add(path);

			if (Files.isDirectory(path)) {
				// TODO
				new Worker(path, paths);
			}
		}

		return paths;
	}

	// TODO Original list(...) method.
	private static void list(Path path, Set<Path> paths) throws IOException {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			for (Path current : stream) {
				paths.add(current);

				if (Files.isDirectory(current)) {
					list(current, paths);
				}
			}
		}
	}

	private static class Worker extends Thread {
		private final Path path;
		private final Set<Path> paths;

		public Worker(Path path, Set<Path> paths) {
			this.path = path;
			this.paths = paths;
			log.debug("Worker for {} created.", path);
		}

		@Override
		public void run() {
			// TODO
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				// TODO
				throw new InterruptedException();
			}
			catch (IOException ex) {
				throw new UncheckedIOException(ex);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			log.debug("Worker for {} finished.", path);
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Path path = Path.of(".");
		Set<Path> actual = list(path);
		Set<Path> expected = SerialDirectoryListing.list(path);
		System.out.println(actual.equals(expected));
	}
}

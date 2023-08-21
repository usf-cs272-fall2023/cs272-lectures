package edu.usfca.cs272;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * This class demonstrates a slightly better option than
 * {@link ThreadedDirectoryListing} for making a multithreaded version of
 * {@link SerialDirectoryListing}.
 *
 * Note: With a tweak to our work queues (moving the pending variable there), we
 * can avoid needing the task manager class entirely!
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class QueuedDirectoryListing {
	/** Logger to use for this class. */
	public static final Logger log = LogManager.getLogger();

	/**
	 * Returns a directory listing for the given path.
	 *
	 * @param path directory to create listing
	 * @return paths found within directory and its subdirectories
	 * @throws InterruptedException from {@link TaskManager#finish()}
	 */
	public static Set<Path> list(Path path) throws InterruptedException {
		Set<Path> paths = new HashSet<>();

		if (Files.exists(path)) {
			paths.add(path);

			if (Files.isDirectory(path)) {
				TaskManager manager = new TaskManager(paths);
				manager.start(path);
				manager.join(); // use our new join method to finish *and* shutdown
			}
		}

		log.debug("Returning {} paths", paths.size());
		return paths;
	}

	/**
	 * Instead of a static worker class, we will create a task manager that can
	 * keep track of pending work.
	 */
	private static class TaskManager {
		/** The shared set of all paths found thus far. */
		private final Set<Path> paths;

		/** The amount of pending (or unfinished) work. */
		private int pending;

		/** The work queue that will handle all of the tasks. */
		private final WorkQueue tasks;

		/**
		 * Initializes a task manager given a set of paths.
		 *
		 * @param paths the set of shared paths to populate
		 */
		private TaskManager(Set<Path> paths) {
			this.paths = paths;
			this.pending = 0;
			this.tasks = new WorkQueue();
		}

		/**
		 * Creates the first task and gives it to the work queue.
		 *
		 * @param path directory to create listing
		 */
		private void start(Path path) {
			tasks.execute(new Task(path));
		}

		/**
		 * The non-static task class that will update the shared paths and pending
		 * members in our task manager instance.
		 */
		private class Task implements Runnable {
			/** The path to add or list. */
			private final Path path;

			/**
			 * Initializes this task.
			 *
			 * @param path the path to add or list
			 */
			public Task(Path path) {
				this.path = path;
				incrementPending();
				log.trace("Created {}", path);
			}

			@Override
			public void run() {
				log.debug("Started {}", path);
				Set<Path> local = new HashSet<>();

				try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
					for (Path current : stream) {
						local.add(current);

						if (Files.isDirectory(current)) {
							tasks.execute(new Task(current));
						}
					}

					synchronized (paths) {
						paths.addAll(local);
					}
				}
				catch (IOException ex) {
					throw new UncheckedIOException(ex);
				}

				log.debug("Finished {}", path);
				decrementPending();
			}
		}

		/**
		 * Rather than having threads wait for each other (undoing our
		 * multithreading), we will wait until all pending work is completed.
		 *
		 * @throws InterruptedException from {@link Thread#wait()}
		 */
		private synchronized void finish() throws InterruptedException {
			while (pending > 0) {
				log.trace("Waiting to finish (pending: {})", pending);
				this.wait();
			}

			log.debug("Pending work finished");
		}

		/**
		 * Safely increments the shared pending variable.
		 */
		private synchronized void incrementPending() {
			pending++;
		}

		/**
		 * Safely decrements the shared pending variable, and wakes up any threads
		 * waiting for work to be completed.
		 */
		private synchronized void decrementPending() {
			assert pending > 0;
			pending--;

			if (pending == 0) {
				this.notifyAll();
			}
		}

		/**
		 * Finishes pending work and then shutdown the work queue.
		 * @throws InterruptedException if interrupted
		 */
		private void join() throws InterruptedException {
			finish();
			tasks.shutdown();
		}
	}

	/**
	 * Tests the directory listing for the current directory.
	 *
	 * @param args unused
	 * @throws InterruptedException from {@link #list(Path)}
	 * @throws IOException from {@link SerialDirectoryListing#list(Path)}
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		Configurator.setAllLevels("edu.usfca.cs272.SerialDirectoryListing", Level.OFF);
		Path path = Path.of(".");
		Set<Path> actual = list(path);
		Set<Path> expected = SerialDirectoryListing.list(path);
		System.out.println(actual.equals(expected));
	}

	/*
	 * So, are we FINALLY at the point where we are writing multithreading code
	 * the way you should in your homework and projects? Almost! The next step is
	 * to eliminate the task manager class entirely by moving the code that deals
	 * with the pending variable to the work queue itself.
	 */
}

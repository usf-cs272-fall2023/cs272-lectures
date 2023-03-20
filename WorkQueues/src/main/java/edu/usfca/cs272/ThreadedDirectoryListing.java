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

/**
 * This class demonstrates a slightly better option than
 * {@link BlockingDirectoryListing} for making a multithreaded version of
 * {@link SerialDirectoryListing}. However, there are still performance
 * issues caused by creating so many little worker thread objects, which have to
 * be cleaned up by our garbage collector later.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class ThreadedDirectoryListing {
	/** Logger to use for this class. */
	private static final Logger log = LogManager.getLogger();

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
				manager.finish();
			}
		}

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

		/**
		 * Initializes a task manager given a set of paths.
		 *
		 * @param paths the set of shared paths to populate
		 */
		private TaskManager(Set<Path> paths) {
			this.paths = paths;
			this.pending = 0;
		}

		/**
		 * Creates and starts the first worker for the directory.
		 *
		 * @param path directory to create listing
		 */
		private void start(Path path) {
			Thread worker = new Worker(path);
			worker.start();
		}

		/**
		 * The non-static worker class that will update the shared paths and pending
		 * members in our task manager instance.
		 */
		private class Worker extends Thread {
			/** The path to add or list. */
			private final Path path;

			// No longer need to pass in a reference to paths!

			/**
			 * Initializes this worker thread.
			 *
			 * @param path the path to add or list
			 */
			public Worker(Path path) {
				this.path = path;

				// now we have to keep track of when we have new "pending" or
				// "unfinished" work
				incrementPending();

				log.debug("Worker for {} created.", path);
			}

			@Override
			public void run() {
				/*
				 * To avoid a synchronized block within a loop, we will create data that
				 * is "local" to this thread (i.e. not shared).
				 */
				Set<Path> local = new HashSet<>();

				try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
					for (Path current : stream) {
						// No synchronization necessary to add to our local set
						local.add(current);

						if (Files.isDirectory(current)) {
							Thread worker = new Worker(current);
							worker.start();

							/*
							 * Note that we no longer need to join() on the worker. That means
							 * other threads can keep on going without having to wait for each
							 * other. Only main needs to wait for all the work to be done.
							 */
						}
					}

					/*
					 * Now, we will block and make our big update. It is less likely
					 * threads will block because they will finish their work at different
					 * times, and we spend less time locking/unlocking and hence less time
					 * causing blocking
					 */
					synchronized (paths) {
						paths.addAll(local);
					}
				}
				catch (IOException ex) {
					throw new UncheckedIOException(ex);
				}

				log.debug("Worker for {} finished.", path);

				// almost done! now we can indicate we have 1 less "pending" work
				decrementPending();
			}
		}

		/*
		 * Since multiple threads need to modify the pending variable, these methods
		 * should be synchronized!
		 */

		/**
		 * Rather than having threads wait for each other (undoing our
		 * multithreading), we will wait until all pending work is completed.
		 *
		 * @throws InterruptedException from {@link Thread#wait()}
		 */
		private synchronized void finish() throws InterruptedException {
			log.debug("Waiting for work...");

			while (pending > 0) {
				// if we put a wait() here... where does the notifyAll() go?
				this.wait();
				log.debug("Woke up with pending at {}.", pending);
			}

			log.debug("Work finished.");
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

			/*
			 * How often should we call notifyAll()? We don't want to overdo it. (See
			 * log if we take out the if pending == 0.)
			 */

			if (pending == 0) {
				this.notifyAll();
			}
		}
	}

	/**
	 * Tests the directory listing for the current directory.
	 *
	 * @param args unused
	 * @throws IOException from {@link SerialDirectoryListing#list(Path)}
	 * @throws InterruptedException from {@link #list(Path)}
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		Path path = Path.of(".");
		Set<Path> actual = list(path);
		Set<Path> expected = SerialDirectoryListing.list(path);

		System.out.println(actual.equals(expected));
	}
}

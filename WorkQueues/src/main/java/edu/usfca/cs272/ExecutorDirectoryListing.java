package edu.usfca.cs272;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class demonstrates a slightly better option than
 * {@link ThreadedDirectoryListing} for making a multithreaded version of
 * {@link SerialDirectoryListing}.
 *
 * Note: With a tweak to our work queues (moving the pending variable there), we
 * can avoid needing the TaskManager class entirely!
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class ExecutorDirectoryListing {
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
		// Protects writes, but still have to be careful about reads.
		// In our case, no reads until multithreading is complete.
		Set<Path> paths = Collections.synchronizedSet(new HashSet<>());

		if (Files.exists(path)) {
			paths.add(path);

			if (Files.isDirectory(path)) {
				TaskManager manager = new TaskManager(paths);
				manager.start(path);
				manager.finish();

				// trigger shutdown and wait until complete
				// (other option is to reuse it)
				manager.executor.shutdown();
				manager.executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
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
		private final AtomicInteger pending;

		/** The work queue that will handle all of the tasks. */
		private final ExecutorService executor;

		/**
		 * Initializes a task manager given a set of paths.
		 *
		 * @param paths the set of shared paths to populate
		 */
		private TaskManager(Set<Path> paths) {
			this.paths = paths;
			this.pending = new AtomicInteger();
			this.executor = Executors.newFixedThreadPool(WorkQueue.DEFAULT);
		}

		/**
		 * Creates the first task and gives it to the work queue.
		 *
		 * @param path directory to create listing
		 */
		private void start(Path path) {
			executor.execute(new Task(path));
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
				log.debug("Task for {} created.", path);
			}

			@Override
			public void run() {
				Set<Path> local = new HashSet<>();

				try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
					for (Path current : stream) {
						local.add(current);

						if (Files.isDirectory(current)) {
							executor.execute(new Task(current));
						}
					}

					paths.addAll(local);
				}
				catch (IOException ex) {
					throw new UncheckedIOException(ex);
				}

				log.debug("Task for {} finished.", path);
				decrementPending();
			}
		}

		/**
		 * Rather than having threads wait for each other (undoing our
		 * multithreading), we will wait until all pending work is completed.
		 *
		 * @throws InterruptedException from {@link Object#wait()}
		 */
		private void finish() throws InterruptedException {
			log.debug("Waiting for work...");

			while (pending.get() > 0) {
				// we still need to synchronize our wait() and notifyAll()
				synchronized (pending) {
					pending.wait();
				}
				log.debug("Woke up with pending at {}.", pending);
			}

			log.debug("Work finished.");
		}

		/**
		 * Safely increments the shared pending variable.
		 */
		private void incrementPending() {
			pending.incrementAndGet();
		}

		/**
		 * Safely decrements the shared pending variable, and wakes up any threads
		 * waiting for work to be completed.
		 */
		private void decrementPending() {
			if (pending.decrementAndGet() == 0) {
				// we still need to synchronize our wait() and notifyAll()
				synchronized (pending) {
					pending.notifyAll();
				}
			}
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
		Path path = Path.of(".");
		Set<Path> actual = list(path);
		Set<Path> expected = SerialDirectoryListing.list(path);

		System.out.println(actual.equals(expected));
	}
}

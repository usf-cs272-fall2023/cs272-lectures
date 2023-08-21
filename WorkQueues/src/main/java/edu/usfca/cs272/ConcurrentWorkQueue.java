package edu.usfca.cs272;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A simple work queue implementation based on the IBM developerWorks article by
 * Brian Goetz. This version uses locks and conditions from the concurrent
 * package. However, it is still up to the user of this class to keep track of
 * whether there is any pending work remaining.
 *
 * @see <a href="https://www.ibm.com/developerworks/library/j-jtp0730/"> Java
 *   Theory and Practice: Thread Pools and Work Queues</a>
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class ConcurrentWorkQueue {
	/** Workers that wait until work (or tasks) are available. */
	private final Worker[] workers;

	/** Queue of pending work (or tasks). */
	private final LinkedList<Runnable> tasks;

	/** Used to signal the queue should be shutdown. */
	private volatile boolean shutdown;

	/** The default number of threads to use when not specified. */
	public static final int DEFAULT = 5;

	/** The lock object to protect access to the queue. */
	private final Lock lock;

	/** The condition controlling when threads are active. */
	private final Condition hasWork;

	/**
	 * Starts a work queue with the default number of threads.
	 *
	 * @see #ConcurrentWorkQueue(int)
	 */
	public ConcurrentWorkQueue() {
		this(DEFAULT);
	}

	/**
	 * Starts a work queue with the specified number of threads.
	 *
	 * @param threads number of worker threads; should be greater than 1
	 */
	public ConcurrentWorkQueue(int threads) {
		this.tasks = new LinkedList<Runnable>();
		this.workers = new Worker[threads];
		this.shutdown = false;
		this.lock = new ReentrantLock();
		this.hasWork = lock.newCondition();

		// start the threads so they are waiting in the background
		for (int i = 0; i < threads; i++) {
			workers[i] = new Worker();
			workers[i].start();
		}
	}

	/**
	 * Adds a work (or task) request to the queue. A worker thread will process
	 * this request when available.
	 *
	 * @param task work request (in the form of a {@link Runnable} object)
	 */
	public void execute(Runnable task) {
		lock.lock(); // replaces synchronized

		try {
			tasks.addLast(task);
			hasWork.signalAll(); // replaces notifyAll
		}
		finally {
			lock.unlock();
		}
	}

	/**
	 * Asks the queue to shutdown. Any unprocessed work will not be finished, but
	 * threads in-progress will not be interrupted.
	 */
	public void shutdown() {
		// safe to do unsynchronized due to volatile keyword
		shutdown = true;

		// still need to signal our threads to wake them up
		lock.lock();

		try {
			hasWork.signalAll();
		}
		finally {
			lock.unlock();
		}
	}

	/**
	 * Returns the number of worker threads being used by the work queue.
	 *
	 * @return number of worker threads
	 */
	public int size() {
		return workers.length;
	}

	/**
	 * Waits until work is available in the work queue. When work is found, will
	 * remove the work from the queue and run it. If a shutdown is detected, will
	 * exit instead of grabbing new work from the queue. These threads will
	 * continue running in the background until a shutdown is requested.
	 */
	private class Worker extends Thread {
		/**
		 * Initializes a worker thread with a custom name.
		 */
		public Worker() {
			setName("Worker" + getName());
		}

		@Override
		public void run() {
			Runnable task = null;

			try {
				while (true) {
					lock.lock();

					try {
						while (tasks.isEmpty() && !shutdown) {
							hasWork.await(); // replaces wait()
						}

						// exit while for one of two reasons:
						// (a) queue has work, or (b) shutdown has been called

						if (shutdown) {
							break;
						}

						task = tasks.removeFirst();
					}
					finally {
						lock.unlock();
					}

					try {
						task.run();
					}
					catch (RuntimeException ex) {
						// catch runtime exceptions to avoid leaking threads
						System.err.printf("Warning: %s encountered an exception while running.%n", this.getName());
					}
				}
			}
			catch (InterruptedException e) {
				System.err.printf("Warning: %s interrupted while waiting.%n", this.getName());
				Thread.currentThread().interrupt();
			}
		}
	}
}

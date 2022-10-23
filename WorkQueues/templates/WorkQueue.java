package edu.usfca.cs272;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorkQueue {
	private final Worker[] workers;
	private final LinkedList<Runnable> tasks;
	private volatile boolean shutdown;

	public static final int DEFAULT = 5;
	private static final Logger log = LogManager.getLogger();

	public WorkQueue() {
		this(DEFAULT);
	}

	public WorkQueue(int threads) {
		this.tasks = new LinkedList<Runnable>();
		this.workers = new Worker[threads];
		this.shutdown = false;

		// TODO
	}

	public void execute(Runnable task) {
		// TODO
	}

	public void shutdown() {
		// TODO
	}

	public int size() {
		return workers.length;
	}

	private class Worker extends Thread {
		@Override
		public void run() {
			Runnable task = null;

			try {
				while (true) {
					// TODO
					throw new InterruptedException();
				}
			}
			catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}

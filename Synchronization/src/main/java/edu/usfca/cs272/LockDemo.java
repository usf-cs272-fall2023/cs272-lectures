package edu.usfca.cs272;

/**
 * This class demonstrates the impact different types of lock/key objects have
 * on whether multiple threads may run synchronized blocks of code at the same
 * time. While some of these approaches may lead to fewer objects or fewer bugs,
 * they may not advised from a security standpoint. Just keep in mind the lock
 * object used has implications beyond synchronization!
 *
 * @see <a href=
 *   "https://wiki.sei.cmu.edu/confluence/pages/viewpage.action?pageId=88487666">
 *   SEI CERT Oracle Coding Standard for Java</a>
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class LockDemo {
	/** The first worker thread. */
	private Worker worker1;

	/** The second worker thread. */
	private Worker worker2;

	/** A static object that may be used for synchronization. */
	private final static Object staticKey = new Object();

	/** An instance object that may be used for synchronization. */
	private final Object instanceKey1;

	/** A different instance object that may be used for synchronization. */
	private final Object instanceKey2;

	/**
	 * Initializes and starts two worker threads.
	 *
	 * @param name the name to use for console output
	 */
	public LockDemo(String name) {
		instanceKey1 = new Object();
		instanceKey2 = new Object();

		/*
		 * Comment in/out different examples and see how the behavior changes.
		 */

		// TF TF TF TF
		worker1 = new Worker(staticKey);
		worker2 = new Worker(staticKey);

		// TF TF TF TF
//		worker1 = new Worker(LockDemo.class);
//		worker2 = new Worker(LockDemo.class);

		// TF TF TF TF
//	worker1 = new Worker(LockDemo.class);
//	worker2 = new Worker(LockDemo.Worker.class);

		// TT FF TT FF
//		worker1 = new Worker(instanceKey1);
//		worker2 = new Worker(instanceKey1);

		// TT FF TT FF
//		worker1 = new Worker(this);
//		worker2 = new Worker(this);

		// TT TT FF FF
//		worker1 = new Worker(new Object());
//		worker2 = new Worker(new Object());

		// TT TT FF FF
//		worker1 = new Worker(instanceKey1);
//		worker2 = new Worker(instanceKey2);

		worker1.setName(name + "1");
		worker2.setName(name + "2");

		worker1.start();
		worker2.start();
	}

	/**
	 * Wait for the workers to terminate.
	 *
	 * @throws InterruptedException if interrupted
	 */
	public void joinAll() throws InterruptedException {
		worker1.join();
		worker2.join();
	}

	/**
	 * A worker thread used to demonstrate synchronization.
	 */
	private class Worker extends Thread {
		/** Lock object used for synchronization */
		private final Object lock;

		/**
		 * Initializes the lock object that will be used by this worker.
		 *
		 * @param lock the lock object to use for synchronization
		 */
		public Worker(Object lock) {
			this.lock = lock;
			// this.lock = new Object();
			// this.lock = this;
		}

		@Override
		public void run() {
			synchronized (lock) {
				System.out.println(this.getName() + " Lock?: " + Thread.holdsLock(lock));

				try {
					// This thread will keep its lock while sleeping!
					// Thread.sleep(...) is used for demonstration, but rarely otherwise.
					Thread.sleep(1000);
				}
				catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}

			System.out.println(this.getName() + " Lock?: " + Thread.holdsLock(lock));
		}
	}

	/**
	 * Demonstrates this class. We have the following threads TRYING to run at the
	 * same time:
	 *
	 * <pre>
	 * +---LockDemo A---+ +---LockDemo B---+
	 * | +-A1-+  +-A2-+ | | +-B1-+  +-B2-+ |
	 * | |    |  |    | | | |    |  |    | |
	 * | +----+  +----+ | | +----+  +----+ |
	 * +----------------+ +----------------+
	 * </pre>
	 *
	 * <p>
	 * Whether A1, A2, B1, B2 are able to run simultaneously (e.g. threads are
	 * able to enter the "locked rooms" setup by each thread) depends on the type
	 * of lock/key used. If there are not enough "keys" then threads will be
	 * blocked.
	 *
	 * @param args unused
	 * @throws InterruptedException if interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		LockDemo a = new LockDemo("A");
		LockDemo b = new LockDemo("B");

		// Wait a little bit, hopefully the threads get a chance to get their locks
		try {
			Thread.sleep(500);
		}
		catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		System.out.println();
		System.out.println("A1 State: " + a.worker1.getState());
		System.out.println("A2 State: " + a.worker2.getState());
		System.out.println("B1 State: " + b.worker1.getState());
		System.out.println("B2 State: " + b.worker2.getState());
		System.out.println();

		a.joinAll();
		b.joinAll();
	}

	/**
	 * Outputs the hashcodes of the lock objects used in this demo.
	 */
	public void outputHashcodes() {
		System.out.println(System.identityHashCode(this));
		System.out.println(System.identityHashCode(instanceKey1));
		System.out.println(System.identityHashCode(instanceKey2));

		System.out.println(System.identityHashCode(staticKey));
		System.out.println(System.identityHashCode(LockDemo.class));

		System.out.println(System.identityHashCode(worker1.lock));
		System.out.println(System.identityHashCode(worker2.lock));
	}
}

package edu.usfca.cs272;

import java.util.Arrays;

public class ThreadStateDemo {
	public static final String FORMAT = "[%-11s] Run by: %-8s  Parent: %s is %-8s  Worker: %s is %s%n";

	private Thread parentThread;
	private Thread workerThread;

	public ThreadStateDemo() throws InterruptedException {
		parentThread = Thread.currentThread();
		workerThread = new Worker();

		// TODO Fill in constructor

//		output("AFTER NEW");
//		output("AFTER START");
//		output("AFTER CALC");
//		output("AFTER JOIN");
	}

	private class Worker extends Thread {
		@Override
		public void run() {
			// TODO Fill in run
			output("AFTER CALC ");
		}
	}

	public static double[] calculate(int size) {
		double[] junk = new double[size];

		for (int i = 0; i < junk.length; i++) {
			junk[i] = Math.random();
		}

		Arrays.sort(junk);
		return junk;
	}

	public void output(String message) {
		System.out.printf(FORMAT, message, Thread.currentThread().getName(),
				parentThread.getName(), parentThread.getState().toString(),
				workerThread.getName(), workerThread.getState().toString());
	}

	public static void main(String[] args) throws InterruptedException {
		new ThreadStateDemo();
	}
}

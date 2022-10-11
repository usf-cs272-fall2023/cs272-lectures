package edu.usfca.cs272;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class demonstrates very basic logging using Log4j2. It is accompanied by
 * the log4j2.xml configuration file.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class Log4jDemo {
	/** The root logger. */
	private static final Logger rootLogger = LogManager.getRootLogger();

	/** A logger specifically for this class. */
	private static final Logger outerLogger = LogManager.getLogger(Log4jDemo.class);

	/**
	 * Outputs log messages using the {@link #outerLogger}.
	 */
	public static void testOuter() {
		outerLogger.trace("Outer Trace");
		outerLogger.debug("Outer Debug");
		outerLogger.info("Outer Info");
		outerLogger.warn("Outer Warn");
		outerLogger.error("Outer Error");
		outerLogger.fatal("Outer Fatal");
	}

	/**
	 * Outputs log messages using the {@link #rootLogger}.
	 */
	public static void testRoot() {
		rootLogger.trace("Root Trace");
		rootLogger.debug("Root Debug");
		rootLogger.info("Root Info");
		rootLogger.warn("Root Warn");
		rootLogger.error("Root Error");
		rootLogger.fatal("Root Fatal");
	}

	/**
	 * Demonstrates that loggers can be created for inner classes as well.
	 */
	public static class Inner {
		/** A logger specifically for this static nested class. */
		private static Logger innerLogger = LogManager.getLogger(Log4jDemo.Inner.class);

		/**
		 * Outputs log messages using the {@link #innerLogger}.
		 */
		public static void testInner() {
			innerLogger.trace("Inner Trace");
			innerLogger.debug("Inner Debug");
			innerLogger.info("Inner Info");
			innerLogger.warn("Inner Warn");
			innerLogger.error("Inner Error");
			innerLogger.fatal("Inner Fatal");
		}
	}

	/**
	 * Demonstrates this class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		System.out.println("Root");
		testRoot();
		System.out.println();

		System.out.println(outerLogger.getName());
		testOuter();
		System.out.println();

		System.out.println(Inner.innerLogger.getName());
		Inner.testInner();
	}
}

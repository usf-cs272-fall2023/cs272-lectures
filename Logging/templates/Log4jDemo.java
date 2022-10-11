package edu.usfca.cs272;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jDemo {
	private static final Logger rootLogger = LogManager.getRootLogger();
	private static final Logger outerLogger = LogManager.getLogger(Log4jDemo.class);

	public static void testOuter() {
		outerLogger.trace("Outer Trace");
		outerLogger.debug("Outer Debug");
		outerLogger.info("Outer Info");
		outerLogger.warn("Outer Warn");
		outerLogger.error("Outer Error");
		outerLogger.fatal("Outer Fatal");
	}

	public static void testRoot() {
		rootLogger.trace("Root Trace");
		rootLogger.debug("Root Debug");
		rootLogger.info("Root Info");
		rootLogger.warn("Root Warn");
		rootLogger.error("Root Error");
		rootLogger.fatal("Root Fatal");
	}

	public static class Inner {
		private static Logger innerLogger = LogManager.getLogger(Log4jDemo.Inner.class);

		public static void testInner() {
			innerLogger.trace("Inner Trace");
			innerLogger.debug("Inner Debug");
			innerLogger.info("Inner Info");
			innerLogger.warn("Inner Warn");
			innerLogger.error("Inner Error");
			innerLogger.fatal("Inner Fatal");
		}
	}

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

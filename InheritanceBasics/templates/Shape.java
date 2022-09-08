package edu.usfca.cs272;

public abstract class Shape {
	private String shapeName;

	public Shape(String shapeName) {
		// TODO Fill in constructor
		debug("Shape(String)");
	}

	public Shape() {
		// TODO Fill in constructor
		debug("Shape()");
	}

	// TODO Fill in methods

	public static boolean debug = true;

	public void debug(String text) {
		if (debug) {
			System.out.println(this.getClass().getName() + ": " + text);
		}
	}
}

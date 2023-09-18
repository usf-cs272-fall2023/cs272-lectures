package edu.usfca.cs272;

public class Shape { // TODO Fix class
	private String shapeName;

	public Shape(String shapeName) {
		this.shapeName = shapeName;
		debug("Shape(String)");
	}

	public Shape() {
		this.shapeName = this.getClass().getSimpleName();
		debug("Shape()");
	}

	// TODO Fix method
	public double area() {
		return -1.0;
	}

	public String getName() {
		return this.shapeName;
	}

	@Override
	public String toString() {
		return getName();
	}

	public static boolean debug = true;

	public void debug(String text) {
		if (debug) {
			System.out.println(this.getClass().getName() + ": " + text);
		}
	}
}

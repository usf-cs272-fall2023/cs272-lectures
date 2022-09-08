package edu.usfca.cs272;

/**
 * Demonstrates several inheritance concepts, including basic inheritance,
 * upcasting and downcasting, nested classes, and anonymous classes.
 *
 * @see ShapeDemo
 * @see Shape
 * @see Rectangle
 * @see Square
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public abstract class Shape {
	/**
	 * Stores the name of the shape.
	 */
	private String shapeName;

	/**
	 * Constructor. Requires name of shape.
	 *
	 * @param shapeName name of shape
	 */
	public Shape(String shapeName) {
		this.shapeName = shapeName;
		debug("Shape(String)");
	}

	/**
	 * Default constructor. Sets name of shape to the simple name of the class.
	 */
	public Shape() {
		this.shapeName = this.getClass().getSimpleName();
		debug("Shape()");
	}

	/**
	 * Calculates and returns the area of the shape. Abstract, so it must be
	 * implemented by non-abstract subclasses.
	 *
	 * @return area of shape
	 */
	public abstract double area();

	/**
	 * Returns the name (circle, square, etc.) of the shape.
	 *
	 * @return name of shape
	 */
	public String getName() {
		return this.shapeName;
	}

	/**
	 * Returns a String representation of this object. Note the @Override
	 * annotation, indicating we are overriding a method provided by a superclass.
	 * You should always use this annotation when appropriate to avoid bugs!
	 *
	 * @return {@link String} representation of object
	 */
	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Used to debug the inheritance hierarchy.
	 */
	public static boolean debug = true;

	/**
	 * Used to debug the inheritance hierarchy.
	 *
	 * @param text the debug message
	 */
	public void debug(String text) {
		if (debug) {
			System.out.println(this.getClass().getName() + ": " + text);
		}
	}
}

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
public class Square extends Rectangle {
	/*
	 * What will be the shapeName in this case?
	 */

	/**
	 * Initializes a square with the specified width.
	 *
	 * @param width the width of the square
	 * @see Rectangle#Rectangle(double, double)
	 */
	public Square(double width) {
		super(width, width);
		debug("Square(double)");
	}

	/**
	 * Initializes a square with no width.
	 *
	 * @see #Square(double)
	 */
	public Square() {
		this(0.0);
		debug("Square()");

		// What about calling super() instead?
	}

	/**
	 * Demonstrates the constructor chaining.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		Shape.debug = true;

		new Rectangle(1.0, 2.0);

		System.out.println();
		new Rectangle();

		System.out.println();
		new Square(3.0);

		System.out.println();
		new Square();
	}
}

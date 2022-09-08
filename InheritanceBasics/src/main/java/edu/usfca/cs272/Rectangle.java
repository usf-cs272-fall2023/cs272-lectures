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
public class Rectangle extends Shape {
	/*
	 * Note that we can declare our own private data. These members are not
	 * accessible by our superclasses or subclasses.
	 */

	/** The rectangle width. */
	private final double width;

	/** The rectangle height. */
	private final double height;

	/*
	 * Note the call to super() in the constructor below. This will make sure the
	 * Shape constructor is called to initialize the shape name.
	 */

	/**
	 * Initializes a rectangle with the specified width and height.
	 *
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 */
	public Rectangle(double width, double height) {
		super(); // call direct superclass constructor
		this.width = width;
		this.height = height;
		debug("Rectangle(double, double)");
	}

	/*
	 * A default constructor still makes sense here. This constructor will call
	 * the other constructor above, which calls the constructor for Shape. This is
	 * an example of chaining constructor calls.
	 */

	/**
	 * Initializes a rectangle with no width or height.
	 *
	 * @see #Rectangle(double, double)
	 */
	public Rectangle() {
		this(0.0, 0.0);
		debug("Rectangle()");
	}

	/*
	 * If you want to be able to create a Rectangle object (i.e. you do not want
	 * to make this class abstract), you MUST implement all of the abstract
	 * methods inherited at this point.
	 */

	@Override
	public double area() {
		return width * height;
	}

	/*
	 * You can override non-abstract methods too if appropriate.
	 */

	@Override
	public String toString() {
		return String.format("%5.2fw x %5.2fh", width, height);
	}

	/*
	 * You can also provide new methods not found in Shape. These will be
	 * inherited by any subclasses of Rectangle.
	 */

	/**
	 * Returns the width of the rectangle.
	 *
	 * @return the width of the rectangle
	 */
	public double width() {
		return width;
	}

	/**
	 * Returns the height of the rectangle.
	 *
	 * @return the height of the rectangle
	 */
	public double height() {
		return height;
	}
}

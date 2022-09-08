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
public class ShapeDemo {
	/** Format string for aligned output. */
	private static final String format = "| %-8s | %-11s | %#010x | %-9s | %-15s | %6.2f |%n";

	/** Divider to separate header row. */
	private static final String divider = "|----------+-------------+------------+-----------+-----------------+--------|";

	/**
	 * Displays basic information about the shape, including the class name, hash
	 * code, type, String representation, and area. Uses casting so that one
	 * method can be provided for any type of Shape object.
	 *
	 * @param variable name of the variable for display purposes
	 * @param shape shape object to display
	 */
	private static void printInfo(String variable, Shape shape) {
		String name = shape.getClass().getName();
		int start = name.lastIndexOf(".");
		System.out.printf(format, variable, name.substring(start + 1), shape.hashCode(),
				shape.getName(), shape.toString(), shape.area());
	}

	/**
	 * Prints out the table header (column names).
	 */
	private static void printHead() {
		System.out.println(divider);
		System.out.printf("| %-8s | %-11s | %-10s | %-9s | %-15s | %-6s |%n",
				"VARIABLE", "CLASS", "HASHCODE", "TYPE", "STRING", "AREA");
		System.out.println(divider);
	}

	/**
	 * Demonstrates inheritance using the Shape class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		Shape.debug = false;

		// print output header
		printHead();

		/*
		 * What will be the class name? (Rectangle) What will be the type?
		 * (Rectangle) What will be the string? (0.00w x 0.00h)
		 */
		Rectangle r1 = new Rectangle();
		System.out.printf(format, "r1", r1.getClass().getSimpleName(), r1.hashCode(),
				r1.getName(), r1.toString(), r1.area());

		/*
		 * What will be the string? (10.50w x 3.20h)
		 */
		Rectangle r2 = new Rectangle(10.5, 3.2);
		System.out.printf(format, "r2", r2.getClass().getSimpleName(), r2.hashCode(),
				r2.getName(), r2.toString(), r2.area());

		/*
		 * Can a square be treated as a rectangle? (Yes, is-a versus has-a) Does the
		 * type of variable or type of referenced object matter? (Referenced object)
		 * What will be the class/type? (Square)
		 */
		Rectangle r3 = new Square(4.6);
		System.out.printf(format, "r3", r3.getClass().getSimpleName(), r3.hashCode(),
				r3.getName(), r3.toString(), r3.area());

		/*
		 * Can we go in the other direction? (No, superclass isn't-a subclass)
		 * Square q0 = new Rectangle();
		 */

		/*
		 * What is downcasting? (Cast from superclass to subclass) Is r3 and q1 the
		 * same object? (Yes, see ID)
		 */
		Square q1 = (Square) r3;
		System.out.printf(format, "q1", q1.getClass().getSimpleName(), q1.hashCode(),
				q1.getName(), q1.toString(), q1.area());

		/*
		 * Why would I use the superclass as the variable type? (Show method) Can I
		 * use printInfo on Square? (Yes, is-a relationship)
		 */
		Square q2 = new Square(9.12);
		printInfo("q2", q2);

		/*
		 * Can I create a Shape instance? (No, abstract) Can I create a Shape
		 * variable? (Yes, superclass/subclass relationship) Can I call printInfo on
		 * Shape? (Yes)
		 */
		Shape s1 = q2;
		printInfo("s1", s1);

		/*
		 * How do we create an inner class? (See A) What will be the class name?
		 * (ShapeDemo$A) What will be the type? (Nested)
		 */
		A s2 = new A();
		printInfo("s2", s2);

		/*
		 * How do we create an anonymous inner class? (See below) What will be the
		 * class name? (ShapeDemo$1) What will be the shape name? (Anon)
		 */
		Shape s3 = new Shape("Anon") {
			@Override
			public double area() {
				return 0.0;
			}
		};

		printInfo("s3", s3);

		System.out.println(divider);
	}

	/**
	 * Demonstrates how to create a static nested class.
	 */
	private static class A extends Shape {
		/**
		 * Initializes this object.
		 */
		public A() {
			super("Nested");
			debug("A()");
		}

		@Override
		public double area() {
			return -1.0;
		}
	}
}

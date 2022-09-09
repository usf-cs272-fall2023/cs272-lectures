package edu.usfca.cs272;

import java.util.Arrays;

/**
 * This class demonstrates the {@link Object} class, that arrays are objects and
 * hence inherit those methods.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class ObjectDemo {
	/**
	 * This method demonstrates that we can create an actual {@link Object} if we
	 * wanted to, and access various properties about that object.
	 *
	 * @see Object
	 */
	public static void objectDemo() {
		Object demo = new Object();

		System.out.printf("%s%n", demo.toString());
		System.out.printf("%s%n", demo.getClass().getName());

		/*
		 * The hashcode is defined as:
		 *
		 * "As far as is reasonably practical, the hashCode method defined by class
		 * Object returns distinct integers for distinct objects."
		 *
		 * If two objects are considered "equal" their hashcodes must be the same,
		 * but if two objects are considered "unequal" it is possible they still
		 * have the same hashcode. In other words, if two hashcodes are not equal,
		 * they are definitely not equal. If the hashcode are equal, they COULD be
		 * (but not guaranteed to be) equal.
		 *
		 * The API documentation used to also include this blurb:
		 *
		 * "This is typically implemented by converting the internal address of the
		 * object into an integer, but this implementation technique is not required
		 * by the JavaTM programming language."
		 *
		 * This means using the typical implementation, the hashcode approximately
		 * let us know where the object is currently stored in memory (and when two
		 * references are likely pointing to the same object in memory). This isn't
		 * perfect though; objects move around in memory and we won't be able to
		 * rely on having a specific implementation of the hashcode.
		 */
		System.out.printf("%x%n", demo.hashCode());
		System.out.printf("%n");
	}

	/**
	 * This method demonstrates that arrays are also considered objects, and have
	 * access to the methods in {@link Object}. The key here is to note that
	 * arrays use the {@code new} keyword to create (unless using shortcut
	 * syntax).
	 *
	 * The default {@code toString()} implementation is not very informative.
	 * Instead, we have to use {@link Arrays#toString(int[])} to get useful output
	 * for arrays.
	 *
	 * @see Object
	 * @see Arrays
	 */
	public static void arrayDemo() {
		int[] demo = new int[] { 1, 2, 3 };

		System.out.printf("%s%n", demo.toString());
		System.out.printf("%s%n", demo.getClass().getName());
		System.out.printf("%x%n", demo.hashCode());
		System.out.printf("%s%n", Arrays.toString(demo));
		System.out.printf("%n");
	}

	/**
	 * This method demonstrates that any class, including this one, is an object
	 * and hence has access to the methods in {@link Object}. We have not created
	 * a constructor, but we can still create an object of this class! The default
	 * constructor in the {@link Object} class will be called in this case.
	 *
	 * @see Object
	 */
	public static void thisDemo() {
		ObjectDemo demo = new ObjectDemo();

		System.out.printf("%s%n", demo.toString());
		System.out.printf("%s%n", demo.getClass().getName());
		System.out.printf("%x%n", demo.hashCode());
		System.out.printf("%n");
	}

	/**
	 * The default {@code equals())} method in the {@link Object} class compares
	 * hash codes, so if the hash codes are equal then the objects are equal. By
	 * default, the {@code ==} operator does the same.
	 */
	public static void equalsDemo() {
		Object demo1 = new Object();
		Object demo2 = new Object();
		Object demo3 = demo2;

		System.out.printf("demo1 = %x%n", demo1.hashCode());
		System.out.printf("demo2 = %x%n", demo2.hashCode());
		System.out.printf("demo3 = %x%n", demo3.hashCode());
		System.out.printf("%n");

		System.out.printf("demo1 == demo2 = %s%n", demo1 == demo2);
		System.out.printf("demo2 == demo3 = %s%n", demo2 == demo3);
		System.out.printf("demo2.equals(demo3) = %s%n", demo2.equals(demo3));
		System.out.printf("%n");
	}

	/**
	 * Runs all of the demos. Comment out the demos if you want to run them one at
	 * a time.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		objectDemo();
		arrayDemo();
		thisDemo();
		equalsDemo();
	}
}

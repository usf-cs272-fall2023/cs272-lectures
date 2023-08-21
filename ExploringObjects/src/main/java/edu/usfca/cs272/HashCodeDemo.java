package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Demonstrates how parameter passing works in Java, including the difference in
 * behavior for immutable versus mutable objects.
 *
 * In particular, mutability is tied to how the state of an object (its
 * allocated memory) is handled. If a new object is created when you try to
 * change a value, then the original object is immutable (its state could not be
 * changed). If the state of the existing object is modified (and a new object
 * is not created) instead, then it is mutable.
 *
 * This is also related to whether it is "safe" to pass a copy of an object
 * reference, because if it is mutable, other classes can modify its value using
 * that reference!
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class HashCodeDemo {
	/**
	 * Demonstrates the methods in this class.
	 *
	 * @param args unused
	 */
	@SuppressWarnings("removal") // Don't do this!
	public static void main(String[] args) {
		/*
		 * Objects in Java may be mutable -OR- immutable. Often, you can find this
		 * information in the API.
		 *
		 * When modifying an immutable object, you are actually creating a new
		 * object. The reference to the old object is broken and updated to the new
		 * object. The garbage collector will clean up the now unused space of the
		 * old object.
		 *
		 * Notice how the identity hash code changes once the Integer value is
		 * modified and reassigned! The original Integer object is not being
		 * modified.
		 */

		Integer integer0 = new Integer(0); // using constructor ON PURPOSE here
		printHash("OUTER BEG", integer0);
		testInteger(integer0);
		printHash("OUTER END", integer0);
		System.out.println();

		/*
		 * Immutable objects with the same value might as well share the same memory
		 * location. This allows Java to optimize its memory usage. That is also why
		 * you should avoid calling "new" when it comes to immutable objects.
		 */

		Integer integer1 = Integer.valueOf(1);
		printHash("VALUE OF", integer1);
		printHash("CALL NEW", new Integer(1));
		System.out.println();

		/*
		 * Strings are immutable objects! As such, their references are safe to pass
		 * since any modification to that String will break the reference to the
		 * original object.
		 */

		String string = "apple";
		printHash("OUTER BEG", string);
		testString(string);
		printHash("OUTER END", string);
		System.out.println();

		/*
		 * Because of this, if you need to make a lot of modification to a String,
		 * use a StringBuilder instead (or a StringBuffer if you are using multiple
		 * threads).
		 *
		 * StringBuilder is mutable, so the function may modify the value of the
		 * original object directly through the passed reference.
		 */

		StringBuilder builder = new StringBuilder("apple");
		printHash("OUTER BEG", builder);
		testBuilder(builder);
		printHash("OUTER END", builder);
		System.out.println();

		/*
		 * Arrays are mutable objects in Java too.
		 */

		String[] array = new String[] { "apple", "banana" };
		printHash("OUTER BEG", array);
		testArray(array);
		printHash("OUTER END", array);
		System.out.println();

		/*
		 * The same holds for an ArrayList and other Collection objects. These
		 * objects are mutable, so passing a reference can be dangerous. A function
		 * could clear the collection completely through the reference.
		 */

		ArrayList<String> list = new ArrayList<String>();
		list.add("apple");
		printHash("OUTER BEG", list);
		testList(list);
		printHash("OUTER END", list);
	}

	/**
	 * Used to demonstrate how the identity hash code is or is not modified within
	 * a method call for this type of object.
	 *
	 * @param value the value passed in from main method
	 */
	public static void testInteger(Integer value) {
		printHash("INNER BEG", value);
		value = 1;
		printHash("INNER END", value);
	}

	/**
	 * Used to demonstrate how the identity hash code is or is not modified within
	 * a method call for this type of object.
	 *
	 * @param value the value passed in from main method
	 */
	public static void testString(String value) {
		printHash("INNER BEG", value);
		value = value.toUpperCase(); // notice the assignment!
		printHash("INNER END", value);
	}

	/**
	 * Used to demonstrate how the identity hash code is or is not modified within
	 * a method call for this type of object.
	 *
	 * @param value the value passed in from main method
	 */
	public static void testBuilder(StringBuilder value) {
		printHash("INNER BEG", value);
		value.append(" banana");
		printHash("INNER END", value);
	}

	/**
	 * Used to demonstrate how the identity hash code is or is not modified within
	 * a method call for this type of object.
	 *
	 * @param value the value passed in from main method
	 */
	public static void testArray(String[] value) {
		printHash("INNER BEG", value);
		value[0] = "carrot";
		printHash("INNER END", value);
	}

	/**
	 * Used to demonstrate how the identity hash code is or is not modified within
	 * a method call for this type of object.
	 *
	 * @param value the value passed in from main method
	 */
	public static void testList(ArrayList<String> value) {
		printHash("INNER BEG", value);
		value.add("banana");
		printHash("INNER BEG", value);
	}

	/**
	 * Shows the identity hash codes and the String representation of the object.
	 *
	 * It is not the focus of this example!
	 *
	 * @param label the label to use in output
	 * @param object the object to display
	 */
	public static void printHash(String label, Object object) {
		Object[] args = { label, System.identityHashCode(object), object };
		System.out.format("%-9s : x%08X : %s %n", args);
	}

	/**
	 * A special version of {@link #printHash(String, Object)} that works for
	 * Object arrays.
	 *
	 * @see #printHash(String, Object)
	 * @param label the label to use in output
	 * @param object the object array to display
	 */
	public static void printHash(String label, Object[] object) {
		Object[] args = { label, System.identityHashCode(object), Arrays.toString(object) };
		System.out.format("%-9s : x%08X : %s %n", args);
	}
}

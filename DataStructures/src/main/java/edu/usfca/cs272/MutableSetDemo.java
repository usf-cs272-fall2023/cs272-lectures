package edu.usfca.cs272;

import java.util.Set;
import java.util.TreeSet;

/**
 * The documentation for {@link Set} states:
 *
 * <blockquote> Note: Great care must be exercised if mutable objects are used
 * as set elements. The behavior of a set is not specified if the value of an
 * object is changed in a manner that affects equals comparisons while the
 * object is an element in the set. A special case of this prohibition is that
 * it is not permissible for a set to contain itself as an element.
 * </blockquote>
 *
 * This example demonstrates what happens if mutable objects are used in a tree
 * set.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class MutableSetDemo {
	/**
	 * Demonstrates what happens when mutable objects are used in a tree set.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		TreeSet<StringBuilder> set = new TreeSet<>();

		set.add(new StringBuilder("banana"));
		set.add(new StringBuilder("cherry"));
		set.add(new StringBuilder("apple"));

		System.out.println(set);

		/*
		 * Unlike the String class, StringBuilder is mutable. So far, there are no
		 * issues caused by using a mutable object in a set. However, what happens
		 * if we mutate (i.e change or modify) one of the objects?
		 */

		set.first().insert(0, "pine");
		System.out.println(set);

		/*
		 * Notice that our set is no longer sorted! So, this tree set is no longer
		 * behaving like a set. In fact... lets try adding another pineapple:
		 */

		set.add(new StringBuilder("pineapple"));
		System.out.println(set);

		/*
		 * Whhhaaaaaat? Now there are duplicates and this isn't even behaving like a
		 * normal set! This is caused by the mutation/change made to one of the
		 * objects. The other objects will behave as normal:
		 */

		set.add(new StringBuilder("banana"));
		System.out.println(set);

		set.add(new StringBuilder("apricot"));
		System.out.println(set);

		/*
		 * Just be careful if you ever decide to use a mutable object in a set, or
		 * as a key in a map. These collections aren't designed to handle mutable
		 * objects and the behavior may be unexpected!
		 */
	}
}

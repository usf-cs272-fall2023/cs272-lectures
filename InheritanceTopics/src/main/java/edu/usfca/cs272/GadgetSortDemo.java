package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * This class demonstrates the {@link Comparable} and {@link Comparator}
 * interfaces and how to use these elements to change how a list of objects is
 * sorted.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class GadgetSortDemo {
	// What should be the type of parameter? List? Set? Collection?

	/**
	 * Able to print to console any {@link Iterable} of {@link Gadget} objects.
	 * Used to debug.
	 *
	 * @param gadgets the iterable collection to be printed to the console
	 */
	public static void printCollection(Iterable<Gadget> gadgets) {
		for (Gadget gadget : gadgets) {
			System.out.printf("id: %2d name: %s\n", gadget.id, gadget.name);
		}
	}

	/**
	 * Demonstrates how to sort the {@link ArrayList} of {@link Gadget} objects
	 * using the {@link Comparable} and {@link Comparator} interfaces and the
	 * {@link Collections#sort(java.util.List)} methods.
	 *
	 * @param args unused
	 *
	 * @see Comparable
	 * @see Comparator
	 * @see Collections#sort(java.util.List)
	 * @see Collections#sort(java.util.List, Comparator)
	 */
	public static void main(String[] args) {
		ArrayList<Gadget> list = new ArrayList<Gadget>();
		list.add(new Gadget(10, "ant"));
		list.add(new Gadget(7, "hippopotamus"));
		list.add(new Gadget(14, "dragonfly"));
		list.add(new Gadget(3, "camel"));

		// unsorted
		System.out.println("ArrayList, Unsorted:");
		printCollection(list);

		// sorted by name length
		System.out.println("\nArrayList, Sorted by ID:");
		Collections.sort(list);
		printCollection(list);

		// sorted by name length
		System.out.println("\nArrayList, Sorted by Name Length:");
		Collections.sort(list, new GadgetComparator());
		printCollection(list);

		// sorted by name
		System.out.println("\nArrayList, Sorted by Name");
		Collections.sort(list, new Comparator<Gadget>() {
			@Override
			public int compare(Gadget w1, Gadget w2) {
				return String.CASE_INSENSITIVE_ORDER.compare(w1.name, w2.name);
			}
		});
		printCollection(list);

		// sorted by id
		System.out.println("\nTreeSet, Sorted by ID:");
		TreeSet<Gadget> set1 = new TreeSet<Gadget>();
		set1.addAll(list);
		printCollection(set1);

		// sorted by name length
		System.out.println("\nTreeSet, Sorted by Name Length:");
		TreeSet<Gadget> set2 = new TreeSet<Gadget>(new GadgetComparator());
		set2.addAll(list);
		printCollection(set2);
	}
}

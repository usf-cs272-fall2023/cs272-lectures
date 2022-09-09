package edu.usfca.cs272;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * This class demonstrates the Generic syntax, and how to use Generic types to
 * improve the generalization of your code.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class GenericTypesDemo {
	/**
	 * This method demonstrates the problem with raw types.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })// Don't do this!
	public static void rawDemo() {
		/*
		 * You can use a "raw type" in your code, but it will generate quite a few
		 * warnings.
		 */
		ArrayList list = new ArrayList();
		list.add("alfalfa");
		list.add("bamboo");
		list.add("cactus");

		/*
		 * The warnings are there for a good reason. Without the type specified, we
		 * could place items in our list that don't match.
		 */
		// list.add(Double.valueOf(3.14));

		/*
		 * And, when we access the elements in our list, the reference will always
		 * be an Object. We have to downcast to access the actual String type, and
		 * this could cause runtime exceptions.
		 */
		for (Object element : list) {
			String casted = (String) element;
			System.out.println(casted.toUpperCase());
		}
	}

	/**
	 * This method demonstrates the benefit of Generic types instead of raw types.
	 */
	public static void listDemo() {
		/*
		 * Instead of raw types, use the Generic types notation to specify what type
		 * of object this ArrayList should store.
		 *
		 * You can initialize as follows:
		 *
		 * ArrayList<String> list = new ArrayList<String>();
		 *
		 * Starting in Java 7, you can simplify this to the line below. You can skip
		 * re-specifying the type in the < > angle brackets if Java can infer the
		 * type.
		 */
		ArrayList<String> list = new ArrayList<>();
		list.add("alfalfa");
		list.add("bamboo");
		list.add("cactus");

		/*
		 * Notice now, we are unable to add another object type to our list.
		 */
		// list.add(Double.valueOf(3.14));

		/*
		 * And, we no longer need to downcast since the ArrayList knows what type of
		 * objects it is storing!
		 */
		for (String element : list) {
			System.out.println(element.toUpperCase());
		}
	}

	/**
	 * This method demonstrates how we can use Generic types in a method to make
	 * that method more generalized. If we use upcasting and return an Object
	 * type, it has to be downcast later on back to its original type. This way,
	 * we always return the object in its own type without forcing casting
	 * (upcasting or downcasting) later on.
	 *
	 * @see #chooseRandom1(Object, Object)
	 * @see #chooseRandom2(Object, Object)
	 * @see #chooseRandom(Iterable)
	 */
	public static void methodDemo() {
		String a = "alfalfa";
		String b = "bamboo";
		Object c = chooseRandom1(a, b);
		String d = chooseRandom2(a, b);

		System.out.println(c);
		System.out.println(d);

		Integer e = 1;
		Integer f = 2;
		Integer g = chooseRandom2(e, f);
		System.out.println(g);

		List<String> words = List.of("ape", "bee", "cat", "dog", "eel", "fox");
		System.out.println(words);
		System.out.println(chooseRandom(words));

		Path path = Path.of(".").toAbsolutePath().normalize();
		System.out.println(path);
		System.out.println(chooseRandom(path));
	}

	/**
	 * Chooses one of the items at random 50% of the time.
	 *
	 * @param item1 the first item
	 * @param item2 the second item
	 * @return either the first or second item chosen randomly
	 *
	 * @see #methodDemo()
	 */
	public static Object chooseRandom1(Object item1, Object item2) {
//		if (Math.random() > 0.5) {
//			return item1;
//		}
//		else {
//			return item2;
//		}

		return Math.random() > 0.5 ? item1 : item2;
	}

	/**
	 * Chooses one of the items at random 50% of the time.
	 *
	 * @param item1 the first item
	 * @param item2 the second item
	 * @return either the first or second item chosen randomly
	 *
	 * @see #methodDemo()
	 */
	public static <A> A chooseRandom2(A item1, A item2) {
//		if (Math.random() > 0.5) {
//			return item1;
//		}
//		else {
//			return item2;
//		}

		return Math.random() > 0.5 ? item1 : item2;
	}

	/**
	 * Randomly chooses one of the elements.
	 *
	 * @param elements the elements to choose from
	 * @return an element from elements chosen randomly
	 *
	 * @see #methodDemo()
	 * @see <a href="https://en.wikipedia.org/wiki/Reservoir_sampling">Reservoir
	 *   Sampling (Wikipedia)</a>
	 */
	public static <A> A chooseRandom(Iterable<A> elements) {
		Iterator<A> iterator = elements.iterator();
		Random r = new Random();

		// if empty return null
		if (!iterator.hasNext()) {
			return null;
		}

		// get first item
		A item = iterator.next();
		int count = 1;

		// loop through remaining items
		while (iterator.hasNext()) {
			A current = iterator.next();
			count++;

			// adjust probability replace item with current by count
			if (r.nextInt(count + 1) < 1) {
				item = current;
			}
		}

		return item;
	}

	/**
	 * This method demonstrates how we can replace restrictions on the Generic
	 * type if necessary.
	 *
	 * @see #chooseMax(Comparable, Comparable)
	 */
	public static void comparableDemo() {
		String a = "alfalfa";
		String b = "bamboo";
		System.out.println(chooseMax(a, b));

		Integer c = 1;
		Integer d = 2;
		System.out.println(chooseMax(c, d));
	}

	/**
	 * Returns the maximum of the two items.
	 *
	 * @param item1 the first item
	 * @param item2 the second item
	 * @return the maximum of the two items
	 *
	 * @see #comparableDemo()
	 * @see Comparable
	 */
	public static <B extends Comparable<B>> B chooseMax(B item1, B item2) {
		if (item1.compareTo(item2) > 0) {
			return item1;
		}
		else {
			return item2;
		}
	}

	/**
	 * This method demonstrates how we can create generalized classes using
	 * Generic types as well, allowing the developer to specify the type of object
	 * or objects that should be used by that class.
	 *
	 * @see Pair
	 */
	public static void classDemo() {
		Pair<String, String> pair1 = new Pair<>("a", "alfalfa");
		Pair<Double, String> pair2 = new Pair<>(Math.PI, "PI");

		System.out.println(pair1);
		System.out.println(pair2);
	}

	/**
	 * This class is part of the {@link GenericTypesDemo#classDemo()} example.
	 *
	 * @param <K> key type
	 * @param <V> value type
	 * @see GenericTypesDemo#classDemo()
	 */
	public static class Pair<K, V> {
		/** The key. */
		private final K one;

		/** The value. */
		private final V two;

		/**
		 * Initializes a pair.
		 *
		 * @param one the key
		 * @param two the value
		 */
		public Pair(K one, V two) {
			this.one = one;
			this.two = two;
		}

		@Override
		public String toString() {
			return "(" + one + ", " + two + ")";
		}
	}

	/**
	 * This method demonstrates how to use wildcards to bound the generic type to
	 * a {@link Number}, and use upcasting.
	 *
	 * @see #sumNumbers1(Iterable)
	 * @see #sumNumbers2(Iterable)
	 */
	public static void wildcardDemo() {
		List<Number> nums = new ArrayList<>();
		nums.add(Double.valueOf(3.14));
		nums.add(Integer.valueOf(42));
		System.out.println(sumNumbers1(nums));
		System.out.println(sumNumbers2(nums));

		List<Integer> ints = new ArrayList<>();
		ints.add(Integer.valueOf(1));
		ints.add(Integer.valueOf(2));
		System.out.println(sumNumbers2(ints));

		/*
		 * With just upcasting alone, we will get compile errors when we try the
		 * following line. This is because while Integer is a subclass of Number,
		 * List<Integer> is *NOT* a subclass of List<Number>. Since they are not
		 * subclasses of each other, upcasting will fail.
		 */
		// System.out.println(sumNumbers1(ints));
	}

	/**
	 * This method is part of the {@link #wildcardDemo()} example.
	 *
	 * @param nums the array of numbers to sum
	 * @return the sum of the numbers as a double value
	 * @see #wildcardDemo()
	 */
	public static double sumNumbers1(Iterable<Number> nums) {
		double sum = 0.0;

		for (Number n : nums) {
			sum += n.doubleValue();
		}

		return sum;
	}

	/**
	 * This method is part of the {@link #wildcardDemo()} example.
	 *
	 * @param nums the array of numbers to sum
	 * @return the sum of the numbers as a double value
	 * @see #wildcardDemo()
	 */
	public static double sumNumbers2(Iterable<? extends Number> nums) {
		double sum = 0.0;

		for (Number n : nums) {
			sum += n.doubleValue();
		}

		return sum;
	}

	/**
	 * Demonstrates the methods in this class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		rawDemo();
		listDemo();
		methodDemo();
		comparableDemo();
		classDemo();
		wildcardDemo();
	}
}

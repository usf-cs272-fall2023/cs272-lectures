package edu.usfca.cs272;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * This class demonstrates several approaches (some that work, some that do not)
 * to iterating through a nested data structure.
 *
 * The level of nesting is usually a clue to how many nested loops you will need
 * to iterate through all of the values.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class IterationDemo {
	/**
	 * Tree is chosen for consistent output, list is chosen just to demonstrate a
	 * different list implementations.
	 */
	private final TreeMap<Integer, LinkedList<String>> results;

	/** Array of words used for demonstration purposes. */
	private final String[] words = { "alligator", "ape", "bear", "bision", "cat",
			"camel", "deer", "dog", "dragonfly", "hippopotamus" };

	/**
	 * Initializes the underlying nested data structure.
	 */
	public IterationDemo() {
		// initialize outer data structure
		results = new TreeMap<Integer, LinkedList<String>>();

		// add words to map based on length of word
		for (String word : words) {
			Integer length = word.length();

			// try to get the current words in the map
			LinkedList<String> values = results.get(length);

			// make sure the inner data structure is initialized
			// without an extra access to the data structure since
			// get() and containsKey() are log(n) time
			if (values == null) {
				values = new LinkedList<>();
				results.put(length, values);
			}

			// add the word to the appropriate list directly
			results.get(length).add(word);
		}

		/*
		 * Why this approach? It can also handles cases where the key exists but for
		 * some reason a value was not initialized, so it is a more defensive coding
		 * style (which assumes there could be bugs in the code, and tries to still
		 * function safely anyway). It also avoids accessing the map more times than
		 * necessary, which for a TreeMap can sometimes matter. (The extra put operation
		 * only happens if necessary, so sometimes there are only 1 log(n) accesses to
		 * the map, instead of 2 log(n) accesses to the map. For code that is run once
		 * on small data, it does not make a noticeable difference in runtime though.
		 * Nevertheless, I like approaches that work in more situations as my default
		 * go-to approach.
		 */
	}

	/**
	 * This example uses a traditional for loop in an attempt to output the map (but
	 * does so incorrectly and inefficiently).
	 */
	public void forExample() {
		System.out.println("Traditional For Example:");
		int iterations = 0; // Iteration number of outer loop

		for (int i = 0; i < results.size(); i++) {
			if (results.containsKey(i)) {
				System.out.print(i + " = ");
				LinkedList<String> list = results.get(i);

				for (int j = 0; j < list.size(); j++) {
					System.out.print(list.get(j) + " ");
				}

				System.out.println();
			}

			iterations++;
		}

		System.out.println("(" + iterations + " iterations)\n");

		/*
		 * Output Incorrect: Many of you may try to use a "traditional" looking for
		 * loop, but this approach does not produce the correct output. It incorrectly
		 * assumes that the last key value is less than the size of the map. In the
		 * demo, the last key value is 12, but the size of the map (determined by the
		 * number of keys) is 5.
		 *
		 * Output Inefficient: It took 5 outer iterations to output 2 results from our
		 * map. We can do much better than that!
		 */
	}

	/**
	 * This example tries instead to use a while loop. The output is correct, but
	 * very inefficient to generate.
	 */
	public void whileExample() {
		System.out.println("While Example:");

		int iterations = 0; // Iteration number of outer loop
		int count = 0; // Number of elements output

		while (count < results.size()) {
			if (results.containsKey(iterations)) {
				System.out.print(iterations + " = ");
				LinkedList<String> list = results.get(iterations);

				for (int j = 0; j < list.size(); j++) {
					System.out.print(list.get(j) + " ");
				}

				System.out.println();
				count++;
			}

			iterations++;
		}

		System.out.println("(" + iterations + " iterations)\n");

		/*
		 * Output Correct: Unlike the previous example, this does produce the correct
		 * output. Instead of looping until a key larger than the size is reached, it
		 * loops until results.size() keys have been found.
		 *
		 * Output Inefficient: This is unnecessarily inefficient. What happens if there
		 * are only two keys... 2 and 100. We loop through 100 times to get two values
		 * out of our data structure! We also have poor runtime with the LinkedList when
		 * we call get(...) like this in a loop, as it has to re-follow links from the
		 * head (or tail) each iteration.
		 */
	}

	/**
	 * This example is correct and efficient, but a bit clunky due to its use of
	 * iterator objects. The next example shows how to improve on this.
	 */
	public void iteratorExample() {
		System.out.println("Iterator Example:");
		int iterations = 0;

		// Gets the iterator for the map keys (show with var as well)
		Iterator<Integer> setIterator = results.keySet().iterator();

		while (setIterator.hasNext()) {
			Integer key = setIterator.next();
			System.out.print(key + " = ");

			// Get the iterator for the inner list
			Iterator<String> listIterator = results.get(key).iterator();

			while (listIterator.hasNext()) {
				System.out.print(listIterator.next() + " ");
			}

			System.out.println();
			iterations++;
		}

		System.out.println("(" + iterations + " iterations)\n");

		/*
		 * Output Correct and Time Efficient: By using iterators, we never loop more
		 * times than necessary. With a sorted data structure, the iterators will go
		 * through the collection in sorted order. With something like a LinkedList, it
		 * doesn't have to re-follow links each iteration. In some cases, iterators
		 * could also pre-fetch the next element since it knows the order of iteration.
		 *
		 * However, the use of iterators for such a simple case may be considered
		 * unnecessarily "clunky" since there are other approaches that involve less
		 * code and object references. The var keyword can help here though!
		 */
	}

	/**
	 * This example demonstrates a basic nested for each loop, which is like using
	 * an iterator but easier to read.
	 */
	public void foreachExample() {
		System.out.println("For Each Example:");
		int iterations = 0;

		for (Integer key : results.keySet()) {
			System.out.print(key + " = ");

			for (String word : results.get(key)) {
				System.out.print(word + " ");
			}

			System.out.println();
			iterations++;
		}

		System.out.println("(" + iterations + " iterations)\n");

		/*
		 * Output Correct and Efficient: This will produce the same output for as an
		 * iterator, but results in easier to read code. It also removes the threat of
		 * off-by-one errors that comes with traditional for loops.
		 *
		 * You can use the for each loop on just about any type of collection, including
		 * arrays. It should be the default loop you use unless you need the index
		 * value or need to iterate in a different order (e.g. every other item).
		 */
	}

	/**
	 * This example demonstrates another approach (which may be slightly more
	 * efficient) to using a for each loop.
	 */
	public void mapentryExample() {
		System.out.println("Map Entry Example");
		int iterations = 0;

		// with Java 10+ we can just say "var entry" to avoid the long type
		// for (Map.Entry<Integer, LinkedList<String>> entry : results.entrySet()) {
		for (var entry : results.entrySet()) {
			System.out.print(entry.getKey() + " = ");

			for (String word : entry.getValue()) {
				System.out.print(word + " ");
			}

			System.out.println();
			iterations++;
		}

		System.out.println("(" + iterations + " iterations)\n");

		/*
		 * This approach grabs both the key and value at the same time, avoiding
		 * multiple accesses to the nested data structure. The var keyword is really
		 * helpful here for preventing too much clutter in the code. Be careful
		 * over-using this keyword... here it improves readability. Used too often, it
		 * hurts readability.
		 */
	}

	/**
	 * This example demonstrates how to use the methods specific to a sorted map,
	 * like TreeMap.
	 */
	public void treemapExample() {
		System.out.println("TreeMap Example");
		int iterations = 0;

		for (Integer i = results.firstKey(); i != null; i = results.higherKey(i)) {
			System.out.print(i + " = ");
			LinkedList<String> words = results.get(i);

			for (int j = 0; j < words.size(); j++) {
				System.out.print(words.get(j) + " ");
			}

			System.out.println();
			iterations++;
		}

		System.out.println("(" + iterations + " iterations)\n");

		/*
		 * We can take advantage of the firstKey(), lastKey(), higherKey(), and
		 * lowerKey() methods to explicitly iterate through the map in sorted order. We
		 * can even use these to iterate through the keys in reverse.
		 *
		 * Note that we must check whether ( i != null ) and not whether ( i <=
		 * results.lastKey() ). Can you figure out why?
		 */
	}

	/**
	 * Shows the output of the examples above.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		IterationDemo demo = new IterationDemo();

		demo.forExample();
		demo.whileExample();

		demo.iteratorExample();
		demo.foreachExample();
		demo.mapentryExample();

		demo.treemapExample();
	}
}

package edu.usfca.cs272;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstrates immutable versus mutable values in a map (attempt to modify an
 * immutable value requires a followup put).
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class MapMutabilityDemo {
	/**
	 * Produces output in a consistent format for this example.
	 *
	 * @param label the output label
	 * @param object the object to output hashcode and toString values
	 */
	public static void output(String label, Object object) {
		System.out.printf("%15s @ x%08x : %s%n", label, System.identityHashCode(object), object.toString());
	}

	/**
	 * Demonstrates this class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		// stores immutable vs mutable values
		Map<String, String> stringMap = new HashMap<>();
		Map<String, StringBuilder> builderMap = new HashMap<>();

		// start case, a ==> ant
		String key = "a";
		String antString = "ant";
		StringBuilder antBuilder = new StringBuilder("ant");

		// output hashcodes for reference
		output("antString", antString);
		output("antBuilder", antBuilder);
		System.out.println();

		// put values into map and output map
		stringMap.put(key, antString);
		builderMap.put(key, antBuilder);

		output("stringMap", stringMap);
		output("builderMap", builderMap);
		System.out.println();

		// confirm the original ant objects are referenced by the map
		output("stringMap.get", stringMap.get(key));
		output("builderMap.get", builderMap.get(key));
		System.out.println();

		// attempt to make a modification and save result
		String stringResult = stringMap.get("a").concat("eater");
		StringBuilder builderResult = builderMap.get("a").append("eater");

		// show that we modified into anteaters
		output("stringResult", stringResult);
		output("builderResult", builderResult);
		System.out.println();

		// however, our map with the immutable value did not update
		output("stringMap", stringMap);
		output("builderMap", builderMap);
		System.out.println();

		// see how the hashcodes did not change?
		output("stringMap.get", stringMap.get(key));
		output("builderMap.get", builderMap.get(key));
		System.out.println();
	}
}

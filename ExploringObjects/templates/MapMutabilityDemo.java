package edu.usfca.cs272;

import java.util.HashMap;
import java.util.Map;

public class MapMutabilityDemo {
	public static void output(String label, Object object) {
		System.out.printf("%15s @ x%08x : %s%n", label, System.identityHashCode(object), object.toString());
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Map<String, String> stringMap = new HashMap<>();
		Map<String, StringBuilder> builderMap = new HashMap<>();

		String key = "a";
		String antString = "ant";
		StringBuilder antBuilder = new StringBuilder("ant");

		output("antString", antString);
		output("antBuilder", antBuilder);
		System.out.println();

		// TODO

		output("stringMap", stringMap);
		output("builderMap", builderMap);
		System.out.println();
	}
}

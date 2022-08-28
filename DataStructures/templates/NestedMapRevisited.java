package edu.usfca.cs272;

import java.util.HashMap;
import java.util.HashSet;

public class NestedMapRevisited {
	public static final String text = "time flies like an arrow fruit flies like a banana";

	public static void main(String[] args) {
		HashMap<Integer, HashSet<String>> words = new HashMap<>();

		for (String word : text.toLowerCase().split(" ")) {
			Integer length = word.length();

			if (!words.containsKey(length)) {
				HashSet<String> set = new HashSet<>();
				set.add(word);
				words.put(length, set);
			}
			else {
				HashSet<String> set = words.get(length);
				set.add(word);
				words.put(length, set);
			}
		}

		for (Integer length : words.keySet()) {
			System.out.printf("%d", length);

			for (String word : words.get(length)) {
				System.out.printf(", %s", word);
			}

			System.out.printf("%n");
		}
	}
}

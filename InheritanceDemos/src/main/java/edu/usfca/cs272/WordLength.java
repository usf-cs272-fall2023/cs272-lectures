package edu.usfca.cs272;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Demonstrates how to create a map of words grouped by length using the
 * {@link WordGroup} interface.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class WordLength implements WordGroup<Integer> {

	/** Stores words by their length. */
	private final HashMap<Integer, HashSet<String>> data;

	/**
	 * Initializes this instance.
	 */
	public WordLength() {
		data = new HashMap<>();
	}

	@Override
	public Integer getGroup(String word) {
		return word.length();
	}

	/*
	 * Note: We can use the more condense and convenient approach to add to our
	 * nested data structure since the hash data structures are fast.
	 */

	@Override
	public boolean addWord(String word) {
		Integer length = getGroup(word);
		data.putIfAbsent(length, new HashSet<>());
		return data.get(length).add(word);
	}

	@Override
	public Set<Integer> viewGroups() {
		return Collections.unmodifiableSet(data.keySet());
	}

	@Override
	public Set<String> viewWords(Integer length) {
		if (data.containsKey(length)) {
			return Collections.unmodifiableSet(data.get(length));
		}

		return Collections.emptySet();
	}

	@Override
	public String toString() {
		return data.toString();
	}

	/*
	 * Note: That is all we need to do here since the has data structures have fast
	 * access. If we were really concerned about efficiency, we could still avoid
	 * creating views when not necessary and override the other default methods.
	 */
}

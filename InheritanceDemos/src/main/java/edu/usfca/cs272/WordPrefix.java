package edu.usfca.cs272;

import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Demonstrates how to create a prefix map using the {@link WordGroup}
 * interface. This data structure is similar to a
 * <a href="https://en.wikipedia.org/wiki/Trie"> trie</a> (pronounced tree or
 * try) used for some forms of autocompletion and spelling.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class WordPrefix implements WordGroup<String> {
	/**
	 * Stores strings by the prefix they start with. The key is the prefix String
	 * and the value is a sorted set of strings that start with that prefix.
	 */
	private final TreeMap<String, TreeSet<String>> data;

	/**
	 * Determines the size of the prefix used. Must be at least one letter. If a
	 * string is smaller than the prefix size, it is skipped. Since the value cannot
	 * be changed after initialization, it can be safely made public.
	 */
	public final int prefixSize;

	/**
	 * Initializes the prefix map using the specified size. If an invalid size is
	 * provided, will initialize a prefix map of size 1.
	 *
	 * @param prefixSize an integer value of 1 or greater for the size
	 */
	public WordPrefix(int prefixSize) {
		this.prefixSize = prefixSize < 1 ? 1 : prefixSize;
		data = new TreeMap<String, TreeSet<String>>();
	}

	/**
	 * Initializes a prefix map with a default size of 1.
	 *
	 * @see #WordPrefix(int)
	 */
	public WordPrefix() {
		this(1);
	}

	/**
	 * Returns the prefix that this word belongs or {@code null} if the word is too
	 * short to store in a prefix map of this size.
	 *
	 * @param word the word to get the prefix for
	 * @return the prefix the word belongs or null if the word is too short
	 */
	@Override
	public String getGroup(String word) {
		if (word != null && word.length() >= prefixSize) {
			return word.substring(0, prefixSize);
		}

		return null;
	}

	/*
	 * Note: Can inherit the Javadoc if we don't need to customize it!
	 */

	@Override
	public boolean addWord(String word) {
		String prefix = getGroup(word);

		if (prefix == null) {
			return false;
		}

		TreeSet<String> words = data.get(prefix);

		if (words == null) {
			words = new TreeSet<String>();
			data.put(prefix, words);
		}

		return words.add(word);
	}

	/*
	 * Note: We do not need to @Override the default addWords method. We can inherit
	 * that default implementation automatically instead.
	 */

	@Override
	public Set<String> viewGroups() {
		return Collections.unmodifiableSet(data.keySet());
	}

	/*
	 * Note: We can actually change the return type as long as it is a subclass or
	 * subtype of the overridden method! We can also change the parameter names.
	 */

	@Override
	public Set<String> viewWords(String prefix) {
		if (data.containsKey(prefix)) {
			return Collections.unmodifiableSet(data.get(prefix));
		}

		return Collections.emptySet();
	}

	/*
	 * Note: We do not HAVE to @Override the has methods since they have default
	 * implementations, but they aren't the most efficient implementation so we will
	 * go ahead and override them anyway.
	 */

	@Override
	public boolean hasGroup(String prefix) {
		return data.containsKey(prefix);
	}

	@Override
	public String hasWord(String word) {
		String prefix = getGroup(word);
		Set<String> words = data.get(prefix);
		return words != null && words.contains(word) ? prefix : null;
	}

	/*
	 * Note: We could also @Override the num methods to avoid creating the
	 * unmodifiable view just to determine the size. We will skip that for now!
	 */

	@Override
	public String toString() {
		return data.toString();
	}
}

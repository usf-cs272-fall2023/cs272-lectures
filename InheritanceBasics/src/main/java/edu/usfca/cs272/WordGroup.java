package edu.usfca.cs272;

import java.util.Collection;
import java.util.Set;

/**
 * Groups together words so that they are accessible by their group.
 *
 * @param <K> the type of group
 *
 * @see PrefixMap
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public interface WordGroup<K> {

	/**
	 * Adds the word to its appropriate group.
	 *
	 * @param word the word to add
	 * @return the group the word was added to or {@code null} if the word could not be added
	 */
	public K addWord(String word);

	/**
	 * Adds multiple words at once.
	 *
	 * @param words the words to add
	 * @see #addWord(String)
	 */
	public default void addWords(String[] words) {
		for (String word : words) {
			addWord(word); // code reuse!
		}
	}

	/**
	 * Returns whether the group exists.
	 *
	 * @param group the group to check
	 * @return {@code true} if the group exists
	 */
	public boolean hasGroup(K group);

	/**
	 * Returns the group if the word exists or null otherwise.
	 *
	 * @param word the word to check
	 * @return the group if the word exists or {@code null} otherwise
	 */
	public K hasWord(String word);

	/**
	 * Returns all of the groups.
	 * @return set of all groups
	 */
	public Set<K> getGroups();

	/**
	 * Returns all of the words for a specific group.
	 *
	 * @param group the group to get words from
	 * @return the collection of words if the group exists or an empty collection
	 */
	public Collection<String> getWords(K group);

}

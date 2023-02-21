package edu.usfca.cs272;

import java.util.Collection;
import java.util.Set;

/**
 * Groups together words so that they are accessible by their group.
 *
 * @param <K> the type of group
 *
 * @see WordPrefix
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public interface WordGroup<K> {
	/**
	 * Returns the group appropriate for the word or {@code null} if the word cannot
	 * be associated with a group.
	 *
	 * @param word the word to get the group for
	 * @return the group the word belongs or {@code null} if the word cannot be
	 *   associated with a group
	 */
	public K getGroup(String word);

	/**
	 * Adds the word to its appropriate group.
	 *
	 * @param word the word to add
	 * @return the {@code true} if the word was added or {@code false} if not
	 */
	public boolean addWord(String word);

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
	 * Returns an unmodifiable view of the groups.
	 *
	 * @return unmodifiable view of the groups
	 */
	public Set<K> viewGroups();

	/**
	 * Returns an unmodifiable view of the words for a specific group.
	 *
	 * @param group the group to view the words from
	 * @return an unmodifiable view of the words if the group exists or an empty
	 *   collection
	 */
	public Collection<String> viewWords(K group);

	/**
	 * Returns whether the group exists.
	 *
	 * @param group the group to check
	 * @return {@code true} if the group exists
	 */
	public default boolean hasGroup(K group) {
		return viewGroups().contains(group);
	}

	/**
	 * Returns the group if the word exists or null otherwise.
	 *
	 * @param word the word to check
	 * @return the group if the word exists or {@code null} otherwise
	 */
	public default K hasWord(String word) {
		K group = getGroup(word);
		return viewWords(group).contains(word) ? group : null;
	}

	/**
	 * Returns the total number of groups stored.
	 *
	 * @return the total number of groups stored
	 */
	public default int numGroups() {
		return viewGroups().size();
	}

	/**
	 * Returns the total number of words stored for a group or {@code 0} if the
	 * group does not exist.
	 *
	 * @param group the group to get the size for
	 * @return the total number of words stored for that group or 0 if the group
	 *   does not exist
	 */
	public default int numWords(K group) {
		return viewWords(group).size();
	}
}

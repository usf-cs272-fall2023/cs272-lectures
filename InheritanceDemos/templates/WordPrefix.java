package edu.usfca.cs272;

import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class WordPrefix /* TODO */ {
	private final TreeMap<String, TreeSet<String>> data;
	public final int prefixSize;

	public WordPrefix(int prefixSize) {
		this.prefixSize = prefixSize < 1 ? 1 : prefixSize;
		data = new TreeMap<String, TreeSet<String>>();
	}

	public WordPrefix() {
		this(1);
	}

	public String getGroup(String word) {
		if (word != null && word.length() >= prefixSize) {
			return word.substring(0, prefixSize);
		}

		return null;
	}

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

	public Set<String> viewGroups() {
		return Collections.unmodifiableSet(data.keySet());
	}

	public Set<String> viewWords(String prefix) {
		Set<String> words = data.get(prefix);
		return words != null ? Collections.unmodifiableSet(words) : Collections.emptySet();
	}

	public boolean hasGroup(String prefix) {
		return data.containsKey(prefix);
	}

	public String hasWord(String word) {
		String prefix = getGroup(word);
		Set<String> words = data.get(prefix);
		return words != null && words.contains(word) ? prefix : null;
	}

	@Override
	public String toString() {
		return data.toString();
	}
}

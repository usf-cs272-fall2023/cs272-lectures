package edu.usfca.cs272;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class PrefixMap {
	private final TreeMap<String, TreeSet<String>> internal;
	public final int prefixSize;

	public PrefixMap(int prefixSize) {
		this.prefixSize = prefixSize < 1 ? 1 : prefixSize;
		internal = new TreeMap<String, TreeSet<String>>();
	}

	public PrefixMap() {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return internal.toString();
	}

	private String getPrefix(String word) {
		if (word != null && word.length() >= prefixSize) {
			return word.substring(0, prefixSize);
		}

		return null;
	}

	public TreeMap<String, TreeSet<String>> getMap() {
		// TODO
		throw new UnsupportedOperationException();
	}

	public Set<String> copyPrefixes() {
		// TODO
		throw new UnsupportedOperationException();
	}

	public Set<String> copyWords(String prefix) {
		// TODO
		throw new UnsupportedOperationException();
	}

	public Set<String> getUnmodifiablePrefixes() {
		// TODO
		throw new UnsupportedOperationException();
	}

	public Set<String> getUnmodifiableWords(String prefix) {
		// TODO
		throw new UnsupportedOperationException();
	}

	public Map<String, TreeSet<String>> getUnmodifiableMap() {
		// TODO
		throw new UnsupportedOperationException();
	}
}

package edu.usfca.cs272;

import java.util.TreeMap;
import java.util.TreeSet;

public class PrefixMap {
	private final TreeMap<String, TreeSet<String>> data;

	public PrefixMap(int prefixSize) {
		this.data = new TreeMap<>();
	}

	public PrefixMap() {
		this(1);
	}
}

package edu.usfca.cs272;

public class PrefixDemo {
	public static void main(String[] args) {
		String[] words = { "ant", "antelope", "ape", "bat", "badger", "cat", "catfish", "dog", "dragonfly" };
		PrefixMap map = new PrefixMap(2);

		try {
			map.addWords(words);
			System.out.println(map);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}

package edu.usfca.cs272;

import java.util.Collection;
import java.util.Set;

public interface WordGroup {
	public Object getGroup(String word);

	public boolean addWord(String word);
	public void addWords(String[] words);

	public Set<Object> viewGroups();
	public Collection<String> viewWords(Object group);

	public boolean hasGroup(Object group);
	public Object hasWord(String word);

	public int numGroups();
	public int numWords(Object group);
}

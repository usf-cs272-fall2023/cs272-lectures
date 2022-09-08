package edu.usfca.cs272;

import java.util.Collection;
import java.util.Set;

public interface WordGroup {
	public Object addWord(String word);
	public void addWords(String[] words);
	public boolean hasGroup(Object group);
	public Object hasWord(String word);
	public Set<Object> getGroups();
	public Collection<String> getWords(Object group);
}

// TODO Add generic type
// TODO Add default implementation
package edu.usfca.cs272;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

/**
 * A customized set class with terrible efficiency that allows access by index,
 * and supports sorted or unsorted ordering. (You would never want to use this
 * implementation---see {@link LinkedHashSet} instead.)
 *
 * @param <E> element type sorted in set
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Spring 2023
 */
public class IndexedSet<E> {
	/** Set of elements */
	private final Set<E> set;

	/**
	 * Initializes an unsorted set.
	 *
	 * @see #IndexedSet(boolean)
	 */
	public IndexedSet() {
		this(false);
	}

	/**
	 * Initializes a sorted or unsorted set depending on the parameter.
	 *
	 * @param sorted if true, will initialize a sorted set
	 */
	public IndexedSet(boolean sorted) {
		set = sorted ? new TreeSet<E>() : new HashSet<E>();
	}

	/**
	 * Adds an element to our set.
	 *
	 * @param element element to add
	 * @return true if the element was added (false if it was a duplicate)
	 *
	 * @see Set#add(Object)
	 */
	public boolean add(E element) {
		return set.add(element);
	}

	/**
	 * Adds the collection of elements to our set.
	 *
	 * @param elements elements to add
	 * @return true if any elements were added (false if were all duplicates)
	 *
	 * @see Set#addAll(Collection)
	 */
	public boolean addAll(Collection<E> elements) {
		return set.addAll(elements);
	}

	/**
	 * Adds values from one {@link IndexedSet} to another.
	 *
	 * @param elements elements to add
	 * @return true if any elements were added (false if were all duplicates)
	 *
	 * @see Set#addAll(Collection)
	 */
	public boolean addAll(IndexedSet<E> elements) {
		return set.addAll(elements.set);
	}

	/**
	 * Returns the number of elements in our set.
	 *
	 * @return number of elements
	 *
	 * @see Set#size()
	 */
	public int size() {
		return set.size();
	}

	/**
	 * Returns whether the element is contained in our set.
	 *
	 * @param element element to search for
	 * @return true if the element is contained in our set
	 *
	 * @see Set#contains(Object)
	 */
	public boolean contains(E element) {
		return set.contains(element);
	}

	/**
	 * Gets the element at the specified index based on iteration order. The element
	 * at this index may change over time as new elements are added.
	 *
	 * @param index index of element to get
	 * @return element at the specified index or null of the index was invalid
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= set.size()) {
			throw new IndexOutOfBoundsException(index);
		}

		return set.stream().skip(index).findFirst().get();
	}

	/**
	 * Gets the first element if it exists.
	 *
	 * @return first element
	 * @throws NoSuchElementException if no first element
	 *
	 * @see #get(int)
	 */
	public E first() throws NoSuchElementException {
		checkEmpty();
		return get(0);
	}

	/**
	 * Gets the last element if it exists.
	 *
	 * @return last element
	 * @throws NoSuchElementException if no last element
	 *
	 * @see #get(int)
	 */
	public E last() throws NoSuchElementException {
		checkEmpty();
		return get(set.size() - 1);
	}

	/**
	 * Throws an exception if the set is empty.
	 *
	 * @throws NoSuchElementException if empty.
	 */
	private void checkEmpty() throws NoSuchElementException {
		if (set.isEmpty()) {
			throw new NoSuchElementException();
		}
	}

	/**
	 * Returns an unsorted copy of this set.
	 *
	 * @return unsorted copy
	 */
	public IndexedSet<E> unsortedCopy() {
		return copy(false);
	}

	/**
	 * Returns a sorted copy of this set.
	 *
	 * @return sorted copy
	 */
	public IndexedSet<E> sortedCopy() {
		return copy(true);
	}

	/**
	 * Returns a copy of the indexed set.
	 *
	 * @param sorted controls whether the copy is sorted or unsorted
	 * @return a sorted copy of the indexed set if the parameter is true, otherwise
	 *   an unsorted copy of the indexed set
	 */
	public IndexedSet<E> copy(boolean sorted) {
		IndexedSet<E> copy = new IndexedSet<>(sorted);
		copy.addAll(set);
		return copy;
	}

	@Override
	public String toString() {
		return set.toString();
	}

	/**
	 * Demonstrates the basic functionality of this class. Used for testing.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		List<String> elements = List.of("ant", "fox", "fly", "bee");

		// demonstrate sorted indexed set
		IndexedSet<String> sorted = new IndexedSet<>(true);
		sorted.addAll(elements);

		System.out.println(sorted);
		System.out.println(sorted.first());
		System.out.println(sorted.get(0));
		System.out.println(sorted.unsortedCopy());

		System.out.println();

		// demonstrate unsorted indexed set
		IndexedSet<String> unsorted = new IndexedSet<>();
		unsorted.addAll(elements);

		System.out.println(unsorted);
		System.out.println(unsorted.last());
		System.out.println(unsorted.get(3));
		System.out.println(unsorted.sortedCopy());

		System.out.println();

		// demonstrate exception
		System.out.println(unsorted.get(10));
	}

	/*
	 * WARNING!
	 *
	 * There really isn't a good reason to use this class... the get() method is
	 * very inefficient. See LinkedHashSet for a better alternative.
	 *
	 * But, it helps us illustrate how to make a class like this thread-safe.
	 */
}

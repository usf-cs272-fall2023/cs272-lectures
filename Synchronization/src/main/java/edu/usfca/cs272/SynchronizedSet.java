package edu.usfca.cs272;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A thread-safe version of {@link IndexedSet} using the synchronized keyword.
 *
 * @param <E> element type
 * @see IndexedSet
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class SynchronizedSet<E> extends IndexedSet<E> {
	/**
	 * Initializes an unsorted synchronized indexed set.
	 */
	public SynchronizedSet() {
		this(false);
	}

	/**
	 * Initializes a sorted or unsorted synchronized index set depending on the
	 * parameter.
	 *
	 * @param sorted if true, will initialize a sorted set
	 */
	public SynchronizedSet(boolean sorted) {
		super(sorted);
	}

	// Use "Override/Implement Methods" under the "Source" menu in Eclipse

	@Override
	public synchronized boolean add(E element) {
		return super.add(element);
	}

	@Override
	public synchronized boolean addAll(Collection<E> elements) {
		return super.addAll(elements);
	}

	@Override
	public synchronized boolean addAll(IndexedSet<E> elements) {
		return super.addAll(elements);
	}

	@Override
	public synchronized int size() {
		return super.size();
	}

	@Override
	public synchronized boolean contains(E element) {
		return super.contains(element);
	}

	@Override
	public synchronized E get(int index) {
		return super.get(index);
	}

	// Must override because of checkEmpty calls

	@Override
	public synchronized E first() throws NoSuchElementException {
		return super.first();
	}

	@Override
	public synchronized E last() throws NoSuchElementException {
		return super.last();
	}

	// Do not need to @Override un/sortedCopy because ONLY calls copy

	@Override
	public synchronized IndexedSet<E> copy(boolean sorted) {
		return super.copy(sorted);
	}

	@Override
	public synchronized String toString() {
		return super.toString();
	}

	/*
	 * Because this class uses synchronized methods instead of a private object
	 * for synchronization, it may be unsafe if the instance of this class is
	 * public (since other code will have access to the lock object and can cause
	 * a deadlock to occur). If properly used as a private member without breaking
	 * encapsulation, it should be fine.
	 */

	/**
	 * Demonstrates this class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		Method[] singleMethods = IndexedSet.class.getDeclaredMethods();
		Method[] threadMethods = SynchronizedSet.class.getDeclaredMethods();

		List<String> expected = Arrays.stream(singleMethods)
				.filter(method -> Modifier.isPublic(method.getModifiers()))
				.filter(method -> !Modifier.isStatic(method.getModifiers()))
				.map(method -> method.getName()).sorted().toList();

		List<String> actual = Arrays.stream(threadMethods)
				.filter(method -> Modifier.isSynchronized(method.getModifiers()))
				.map(method -> method.getName()).sorted().toList();

		System.out.println("Original Methods:");
		System.out.println(expected);

		System.out.println();
		System.out.println("Synchronized Methods:");
		System.out.println(actual);
	}
}

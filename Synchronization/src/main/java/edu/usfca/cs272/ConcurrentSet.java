package edu.usfca.cs272;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A thread-safe version of {@link IndexedSet} using a read/write lock.
 *
 * @param <E> element type
 * @see IndexedSet
 * @see ReadWriteLock
 * @see ReentrantReadWriteLock
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class ConcurrentSet<E> extends IndexedSet<E> {
	/** The lock object to use. */
	private ReentrantReadWriteLock lock;

	/**
	 * Initializes an unsorted synchronized indexed set.
	 */
	public ConcurrentSet() {
		this(false);
	}

	/**
	 * Initializes a sorted or unsorted synchronized index set depending on the
	 * parameter.
	 *
	 * @param sorted if true, will initialize a sorted set
	 */
	public ConcurrentSet(boolean sorted) {
		super(sorted);
		lock = new ReentrantReadWriteLock();
	}

	@Override
	public boolean add(E element) {
		// lock outside of try/catch (if this fails, don't enter the try/catch)
		lock.writeLock().lock();

		try {
			// always call super in a try in case of runtime exceptions
			return super.add(element);
		}
		finally {
			// make sure the lock is always unlocked even if there is an exception
			lock.writeLock().unlock();
		}
	}

	@Override
	public boolean addAll(Collection<E> elements) {
		lock.writeLock().lock();

		try {
			return super.addAll(elements);
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public boolean addAll(IndexedSet<E> elements) {
		lock.writeLock().lock();

		try {
			return super.addAll(elements);
		}
		finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public int size() {
		lock.readLock().lock();

		try {
			return super.size();
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public boolean contains(E element) {
		lock.readLock().lock();

		try {
			return super.contains(element);
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public E get(int index) {
		lock.readLock().lock();

		try {
			return super.get(index);
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public E first() throws NoSuchElementException {
		lock.readLock().lock();

		try {
			return super.first();
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public E last() throws NoSuchElementException {
		lock.readLock().lock();

		try {
			return super.last();
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public IndexedSet<E> copy(boolean sorted) {
		lock.readLock().lock();

		try {
			return super.copy(sorted);
		}
		finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public String toString() {
		lock.readLock().lock();

		try {
			return super.toString();
		}
		finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * Demonstrates this class.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		Method[] singleMethods = IndexedSet.class.getDeclaredMethods();
		Method[] threadMethods = ConcurrentSet.class.getDeclaredMethods();

		List<String> expected = Arrays.stream(singleMethods)
				.filter(method -> Modifier.isPublic(method.getModifiers()))
				.filter(method -> !Modifier.isStatic(method.getModifiers()))
				.map(method -> method.getName()).sorted().toList();

		List<String> actual = Arrays.stream(threadMethods)
				.filter(method -> Modifier.isPublic(method.getModifiers()))
				.filter(method -> !Modifier.isStatic(method.getModifiers()))
				.map(method -> method.getName()).sorted().toList();

		System.out.println("Original Methods:");
		System.out.println(expected);

		System.out.println();
		System.out.println("Overridden Methods:");
		System.out.println(actual);
	}
}

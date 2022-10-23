package edu.usfca.cs272;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class IndexedSet<E> {
	private final Set<E> set;

	public IndexedSet() {
		this(false);
	}

	public IndexedSet(boolean sorted) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public boolean add(E element) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public boolean addAll(Collection<E> elements) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public boolean addAll(IndexedSet<E> elements) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public E get(int index) throws IndexOutOfBoundsException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public E first() throws NoSuchElementException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public E last() throws NoSuchElementException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	private void checkEmpty() throws NoSuchElementException {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public IndexedSet<E> unsortedCopy() {
		return copy(false);
	}

	public IndexedSet<E> sortedCopy() {
		return copy(true);
	}

	public IndexedSet<E> copy(boolean sorted) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public int size() {
		return set.size();
	}

	public boolean contains(E element) {
		return set.contains(element);
	}

	@Override
	public String toString() {
		return set.toString();
	}

	public static void main(String[] args) {
		List<String> elements = List.of("ant", "fox", "fly", "bee");

		IndexedSet<String> sorted = new IndexedSet<>(true);
		sorted.addAll(elements);

		System.out.println(sorted);
		System.out.println(sorted.first());
		System.out.println(sorted.get(0));
		System.out.println(sorted.unsortedCopy());

		System.out.println();

		IndexedSet<String> unsorted = new IndexedSet<>();
		unsorted.addAll(elements);

		System.out.println(unsorted);
		System.out.println(unsorted.last());
		System.out.println(unsorted.get(3));
		System.out.println(unsorted.sortedCopy());

		System.out.println();

		System.out.println(unsorted.get(10));
	}
}

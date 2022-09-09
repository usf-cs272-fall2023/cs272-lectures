package edu.usfca.cs272;

/**
 * A simple class that knows how to compare against other objects of this class
 * by implementing the {@link Comparable} interface.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2022
 */
public class Gadget implements Comparable<Gadget> {
	/** The gadget ID. */
	public int id;

	/** The gadget name. */
	public String name;

	/**
	 * Initializes the gadget.
	 *
	 * @param id the gadget id
	 * @param name the gadget name
	 */
	public Gadget(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * By default, {@link #Gadget} objects will be sorted by the gadget id.
	 */
	@Override
	public int compareTo(Gadget other) {
		return Integer.compare(this.id, other.id);
	}
}

package edu.usfca.cs272;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 *    0 1 2 3 4 5 6 7 8 9
 *
 * ...versus...
 *
 *    0 1 2 3
 *    9     4
 *    8 7 6 5
 *
 */
public class BoundedBuffer<E> {
	private Object[] buffer;

	private int beg;
	private int end;
	private int num;
	private int max;

	private static Logger log = LogManager.getLogger();

	public BoundedBuffer(int bufferSize) {
		buffer = newArray(bufferSize);
		beg = 0;
		end = 0;
		num = 0;
		max = buffer.length;
	}

	public synchronized void put(E item) throws InterruptedException {
		log.debug("put(): adding {} in buffer.", item);

		// TODO Fill in put()

		log.debug("put(): buffer now has {} elements.", num);
		log.debug("put(): range is now ({}, {}).", beg, end);
	}

	public synchronized void putAll(E[] items) throws InterruptedException {
		for (E item : items) {
			this.put(item);
		}
	}

	public synchronized E get() throws InterruptedException {
		log.debug("get(): getting {} from buffer.", buffer[beg]);

		// TODO Fill in get()

		log.debug("get(): buffer now has {} elements.", num);
		log.debug("get(): range is now ({}, {}).", beg, end);

		return null; // TODO Fix return
	}

	private static <E> E[] newArray(int length, E... unused) {
		return Arrays.copyOf(unused, length);
	}
}

package edu.usfca.cs272;

import java.util.stream.IntStream;

/**
 * Demonstrates infinite streams.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2023
 */
public class InfiniteStreamDemo {
	/**
	 * Reverses the digits provided. For example, 12345 becomes 54321. Works for
	 * both positive and negative numbers.
	 *
	 * @param digits the digits to reverse
	 * @return the reversed digits
	 */
	public static long reverse(long digits) {
		long sign = 1;
		long reversed = 0;

		// handle negative numbers
		if (digits < 0) {
			sign = -1;
			digits = Math.abs(digits);
		}

		// keep going while there are digits left
		while (digits > 0) {
			// get the last digit
			long digit = digits % 10;
			// shift digits already added to left and add new digit
			reversed = reversed * 10 + digit;
			// remove last digit
			digits /= 10;
		}

		// make number negative if necessary
		return sign * reversed;
	}

	/**
	 * Outputs palindromic squares until the results get too large and cause an
	 * overflow.
	 *
	 * @param args unused
	 * @throws ArithmeticException if an integer overflow occurs
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/Palindromic_number">Palindromic Number</a>
	 * @see <a href="https://oeis.org/A002779">Palindromic squares</a>
	 */
	public static void main(String[] args) throws ArithmeticException {
		// use iterate() to generate an unbounded number of long values
		IntStream.iterate(1, i -> i + 1)
//				.limit(10) // limit it at the start to test
//				.parallel() // impacts order of output (careful!)
				.map(i -> i * i) // calculate square of number (ignore overflow)
//				.map(i -> Math.multiplyExact(i, i)) // square, but detect overflows
				.filter(i -> i == reverse(i)) // only keep those numbers that are palindromes
//				.limit(10) // compare to limiting at the start
				.forEach(System.out::println);

		// note that the exception causes the stream to close
		// exceptions (especially checked) and streams do not get along well
	}
}

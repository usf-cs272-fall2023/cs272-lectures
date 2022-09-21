package edu.usfca.cs272;

public class InfiniteStreamDemo {
	public static long reverse(long digits) {
		long sign = 1;
		long reversed = 0;

		if (digits < 0) {
			sign = -1;
			digits = Math.abs(digits);
		}

		while (digits > 0) {
			long digit = digits % 10;
			reversed = reversed * 10 + digit;
			digits /= 10;
		}

		return sign * reversed;
	}

	public static void main(String[] args) throws ArithmeticException {
		// TODO
	}
}

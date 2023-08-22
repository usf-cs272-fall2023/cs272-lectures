package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Problem1e {
	public static Set<Integer> getMultiples(int value, int max) {
		// TODO Fill in solution
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	public static int sumMultiples(Collection<Integer> values, int max) {
		// TODO Fill in solution
		throw new UnsupportedOperationException("Not yet implemented.");
	}

	public static void main(String[] args) {
		int max = 0;
		List<Integer> values = new ArrayList<>();

		try {
			max = Integer.parseInt(args[0]);

			if (max < 0) {
				throw new NumberFormatException("Integer value must be non-negative.");
			}

			for (int i = 1; i < args.length; i++) {
				int value = Integer.parseInt(args[i]);

				if (value < 0) {
					throw new NumberFormatException("Integer value must be non-negative.");
				}

				values.add(value);
			}

			int result = sumMultiples(values, max);
			String format = "The sum of multiples of %s less than %d is %d.";
			System.out.printf(format, values.toString(), max, result);
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("At least two values must be provided.");
		}
		catch (NumberFormatException e) {
			System.err.println("All values must be non-negative integers.");
		}
	}
}

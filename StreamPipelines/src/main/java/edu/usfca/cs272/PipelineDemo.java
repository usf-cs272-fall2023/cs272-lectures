package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Demonstrates using stream pipelines for the first time.
 */
public class PipelineDemo {
	/** List of birds. */
	public static final List<String> birds = List.of("albatross", "birds", "blackbird", "bluebird",
			"cardinal", "chickadee", "crane", "crow", "cuckoo", "dove", "duck",
			"eagle", "egret", "falcon", "finch", "goose", "gull", "hawk", "heron",
			"hummingbird", "ibis", "kingfisher", "loon", "magpie", "mallard",
			"meadowlark", "mockingbird", "nighthawk", "osprey", "owl", "pelican",
			"pheasant", "pigeon", "puffin", "quail", "raven", "roadrunner", "robin",
			"sandpiper", "sparrow", "starling", "stork", "swallow", "swan", "tern",
			"turkey", "vulture", "warbler", "woodpecker", "wren", "yellowthroat");

	/** Output all birds using multiple approaches. */
	public static void outputBirds() {
		System.out.println("## OUTPUT ALL BIRDS ##");

		for (String bird : birds) {
			System.out.print(bird.concat(" "));
		}

		System.out.println();

		birds.stream().map(bird -> bird.concat(" ")).forEach(System.out::print);
		System.out.println();
	}

	/** Output all birds with double letters. */
	public static void outputDoubles() {
		System.out.println("## OUTPUT BIRDS WITH DOUBLE LETTERS ##");

		for (String bird : birds) {
			if (bird.matches(regex)) {
				System.out.print(bird.concat(" "));
			}
		}

		System.out.println();

		birds.stream()
				.filter(bird -> bird.matches(regex))
				.map(bird -> bird.concat(" "))
				.forEach(System.out::print);

		System.out.println();
	}

	/** Outputs birds with double letters reusing lambda expressions. */
	public static void outputLambdas() {
		System.out.println("## OUTPUT REUSING LAMBDA EXPRESSIONS ##");

		for (String bird : birds) {
			if (hasDoubles.test(bird)) {
				System.out.print(addSpace.apply(bird));
				// System.out.print(addSpaceString.transform(bird));
			}
		}

		System.out.println();

		birds.stream().filter(hasDoubles).map(addSpace).forEach(System.out::print);
		System.out.println();
	}

	/** Collects all birds with double letters into lists. */
	public static void collectDoubles() {
		System.out.println("## COLLECT BIRDS WITH DOUBLE LETTERS ##");

		List<String> withLoop = new ArrayList<>();

		for (String bird : birds) {
			if (hasDoubles.test(bird)) {
				withLoop.add(bird);
			}
		}

		System.out.println(withLoop);

		// stream approach with side effects
		List<String> withSideEffects = new ArrayList<>();
		birds.stream().filter(hasDoubles).forEach(withSideEffects::add);
		System.out.println(withSideEffects);

		// stream approach using collectors
		List<String> withCollectors = birds.stream()
				.filter(hasDoubles)
				.collect(Collectors.toList());

		System.out.println(withCollectors);

		// stream approach using shortcut collectors
		List<String> withShortcut = birds.stream().filter(hasDoubles).toList();
		System.out.println(withShortcut);

		// example 1 liner approach
		System.out.println(birds.stream().filter(hasDoubles).toList());
	}

	/** Counts all birds with double letters. */
	public static void aggregateCount() {
		System.out.println("## COUNT BIRDS WITH DOUBLE LETTERS ##");

		long withLoop = 0;

		for (String bird : birds) {
			if (hasDoubles.test(bird)) {
				withLoop++;
			}
		}

		System.out.println("Found: " + withLoop);

		/*
		 * This is an approach that does not work. Streams require "effectively final"
		 * objects, even in the terminal operation. This will cause an issue whenever we
		 * need a new value of an immutable type. It is possible to make this work with
		 * an "adder" or "accumulator" object instead (see LongAdder), but there is a
		 * simpler way to do this in a stream!
		 */
//		long withSideEffect = 0;
//		birds.stream().filter(hasDoubles).forEach(bird -> { withSideEffect++; });

		// approach with streams
		long withStream = birds.stream().filter(hasDoubles).count();
		System.out.println("Found: " + withStream);

		// approach with parallel streams
		long withParallel = birds.stream().parallel().filter(hasDoubles).count();
		System.out.println("Found: " + withParallel);
	}

	/**
	 * Runs each of the demos.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {
		outputBirds();
		System.out.println();

		outputDoubles();
		System.out.println();

		outputLambdas();
		System.out.println();

		collectDoubles();
		System.out.println();

		aggregateCount();
		System.out.println();
	}

	/** Regular expression for finding double letters. */
	public static final String regex = ".*(\\w)\\1.*";

	/** Lambda expression for finding double letters. */
	public static final Predicate<String> hasDoubles = bird -> bird.matches(regex);

	/** Generic function for adding a space to a String. */
	public static final Function<String, String> addSpace = bird -> bird.concat(" ");

	/** Custom function for adding a space to a String. */
	public static final StringFunction addSpaceString = bird -> bird.concat(" ");

	/**
	 * Simple interface for transforming Strings.
	 */
	@FunctionalInterface
	public interface StringFunction {
		/**
		 * Transforms in input String into an output String.
		 *
		 * @param input the input String
		 * @return the output String
		 */
		public String transform(String input);
	}
}

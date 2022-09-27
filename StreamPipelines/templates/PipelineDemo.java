package edu.usfca.cs272;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class PipelineDemo {
	public static final List<String> birds = List.of("albatross", "birds", "blackbird", "bluebird",
			"cardinal", "chickadee", "crane", "crow", "cuckoo", "dove", "duck",
			"eagle", "egret", "falcon", "finch", "goose", "gull", "hawk", "heron",
			"hummingbird", "ibis", "kingfisher", "loon", "magpie", "mallard",
			"meadowlark", "mockingbird", "nighthawk", "osprey", "owl", "pelican",
			"pheasant", "pigeon", "puffin", "quail", "raven", "roadrunner", "robin",
			"sandpiper", "sparrow", "starling", "stork", "swallow", "swan", "tern",
			"turkey", "vulture", "warbler", "woodpecker", "wren", "yellowthroat");

	public static void outputBirds() {
		System.out.println("## OUTPUT ALL BIRDS ##");

		for (String bird : birds) {
			System.out.print(bird + " ");
		}

		System.out.println();

		// TODO Add stream approach
	}

	public static void outputDoubles() {
		System.out.println("## OUTPUT BIRDS WITH DOUBLE LETTERS ##");

		for (String bird : birds) {
			if (bird.matches(regex)) {
				System.out.print(bird + " ");
			}
		}

		System.out.println();

		// TODO Add stream approach
	}

	public static final String regex = ".*(\\w)\\1.*";

	public static void outputLambdas() {
		System.out.println("## OUTPUT REUSING LAMBDA EXPRESSIONS ##");

		for (String bird : birds) {
			// TODO Fix method calls
			if (bird.matches(regex)) {
				System.out.print(bird + " ");
			}
		}

		System.out.println();

		// TODO Add stream approach
	}

	public static final Predicate<String> hasDoubles = bird -> bird.matches(regex);
	public static final Function<String, String> addSpace = bird -> bird + " ";
	public static final StringFunction addSpaceString = bird -> bird + " ";

	@FunctionalInterface
	public interface StringFunction {
		public String transform(String input);
	}

	public static void collectDoubles() {
		System.out.println("## COLLECT BIRDS WITH DOUBLE LETTERS ##");

		List<String> withLoop = new ArrayList<>();

		for (String bird : birds) {
			if (hasDoubles.test(bird)) {
				withLoop.add(bird);
			}
		}

		System.out.println(withLoop);

		// TODO Fill in approach
		List<String> withSideEffects = new ArrayList<>();
		System.out.println(withSideEffects);

		// TODO Fill in approach
		List<String> withCollectors = null;
		System.out.println(withCollectors);

		// TODO Fill in approach
		List<String> withShortcut = null;
		System.out.println(withShortcut);

		// TODO Fill in 1 liner approach
	}

	public static void aggregateCount() {
		System.out.println("## COUNT BIRDS WITH DOUBLE LETTERS ##");

		long withLoop = 0;

		for (String bird : birds) {
			if (hasDoubles.test(bird)) {
				withLoop++;
			}
		}

		System.out.println("Found: " + withLoop);

		long withSideEffect = 0;
		// TODO Fill in approach

		// TODO Fill in approach
		long withStream = 0;
		System.out.println("Found: " + withStream);

		// TODO Fill in approach
		long withParallel = 0;
		System.out.println("Found: " + withParallel);
	}

	public static void main(String[] args) {
		outputBirds();
		System.out.println();

//		outputDoubles();
//		System.out.println();

//		outputLambdas();
//		System.out.println();

//		collectDoubles();
//		System.out.println();

//		aggregateCount();
//		System.out.println();
	}
}

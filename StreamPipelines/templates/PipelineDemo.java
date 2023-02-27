package edu.usfca.cs272;

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

	public static final String regex = ".*(\\w)\\1.*";

	public static void main(String[] args) {
		for (String bird : birds) {
			System.out.print(bird.concat(" "));
		}

		System.out.println();

		// TODO Add alternative approach here

//		List<String> filtered = new ArrayList<>();
//		System.out.println(filtered);

//		int doubles = 0;
//		System.out.println(doubles);
	}

	public static final Predicate<String> hasDoubles = null;
	public static final Function<String, String> addSpace = null;
	public static final StringFunction addSpaceString = null;

	@FunctionalInterface
	public interface StringFunction {
		public String transform(String input);
	}
}

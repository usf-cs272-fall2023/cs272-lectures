package edu.usfca.cs272;

public class RegexClasses extends RegexHelper {
	public static void main(String[] args) {
		// Sally Sue sells 76 sea-shells, by   the sea_shore.
		console.println(RegexHelper.sample);
		console.println();
		
		showMatches(sample, "s");
		console.println();

//		showMatches(sample, "[sS]");
//		console.println();
	}
}

package edu.usfca.cs272;

public enum AdventureRoom {
	// +-----------+-----------+-----------+
	// | START (0) | CHAIR (1) | GROWL (2) |
	// +-----------+-----------+-----------+
	// | BLACK (3) | CHILL (4) | TIGER (5) |
	// +-----------+-----------+-----------+
	// | YUMMY (6) | SMELL (7) | NOISE (8) |
	// +-----------+-----------+-----------+

	TODO;

//	START_ROOM("The house appears abandoned, but your nose tells you food is in here somewhere.", false),
//	CHAIR_ROOM("You find a comfortable chair. Your stomach growls.", false),
//	GROWL_ROOM("You hear a faint growling noise. It isn't your stomach this time.", false),
//	BLACK_ROOM("Oh no! You have fallen through a black hole!", true),
//	CHILL_ROOM("You feel a slight chill. Was there a window open somewhere?", false),
//	TIGER_ROOM("Oh no! You have been eaten by a tiger. It was also quite hungry.", true),
//	YUMMY_ROOM("Congratulations! You found the meal and it looks delicious!", true),
//	SMELL_ROOM("You smell something pleasant.", false),
//	NOISE_ROOM("You hear a faint noise, almost like purring.", false);

	public boolean canMove(Direction direction) {
		return false;
		// default -> throw new IllegalStateException(direction.toString());
	}

	public AdventureRoom moveRoom(Direction direction) {
		return null;
	}
}

package ads.graphs;

/* Represents any graph's edge direction */

public enum Direction {
	NONE(0), LEFT(1), RIGHT(2), BOTH(3);
	
	private final int val;
	
	Direction(int val) {
		this.val = val;
	}
	
	public static Direction from(int val) {
		switch (val) {
			case 0: return NONE;
			case 1: return LEFT;
			case 2: return RIGHT;
			case 3: return BOTH;
			default: throw new IllegalArgumentException("Valid enum values are [0,3] only.");
		}
	}
	
	public Direction and(Direction other) {
		return from(val|other.val);
	}
	
	public void check(Runnable ifLeft, Runnable ifRight) {
		boolean override = equals(NONE) || equals(BOTH);
		if (override || equals(LEFT))
			ifLeft.run();
		if (override || equals(RIGHT))
			ifRight.run();
	}
}
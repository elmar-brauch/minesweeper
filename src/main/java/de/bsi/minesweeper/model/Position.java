package de.bsi.minesweeper.model;

public record Position (int row, int column) {
	
	private static final String REGEX_POSITION = "\\d+:\\d+";
	
	public static Position parse(String positionAsString) {
		if (positionAsString == null || !positionAsString.matches(REGEX_POSITION))
			throw new IllegalArgumentException("Position as String must match RegEx" + REGEX_POSITION);
		var rowAndCol = positionAsString.split(":");
        return new Position(Integer.parseInt(rowAndCol[0]), Integer.parseInt(rowAndCol[1]));
	}

	@Override
	public String toString() {
		return row + ":" + column;
	}
}
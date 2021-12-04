package de.bsi.minesweeper.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class Position {
	
	private static final String REGEX_POSITION = "\\d+:\\d+";
	
	private final int row;
	private final int column;
	
	public static Position parse(String positionAsString) {
		if (positionAsString == null || !positionAsString.matches(REGEX_POSITION))
			throw new IllegalArgumentException("Position as String must match RegEx" + REGEX_POSITION);
		var rowAndCol = positionAsString.split(":");
        return new Position(Integer.parseInt(rowAndCol[0]), Integer.parseInt(rowAndCol[1]));
	}
	
	public static Position of(int row, int column) {
		return new Position(row, column);
	}
	
	@Override
	public String toString() {
		return row + ":" + column;
	}

}

package de.bsi.minesweeper.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

// Position class is simplified for this exercise.
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class Position {
	
	private final int row;
	private final int column;
	
	public static Position of(int row, int column) {
		return new Position(row, column);
	}

}

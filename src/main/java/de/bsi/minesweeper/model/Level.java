package de.bsi.minesweeper.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Level {
	EASY(5, 5, 4),
	MEDIUM(10, 12, 25),
	HARD(25, 20, 125);
	
	private int columns;
	private int rows;
	private int mines;
}

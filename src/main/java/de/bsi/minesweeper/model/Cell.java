package de.bsi.minesweeper.model;

import lombok.Getter;

// Cell class is simplified for this exercise.
@Getter
public class Cell {
	
	private final Position position;
	
	public Cell(Position position) {
		this.position = position;
	}
	
}

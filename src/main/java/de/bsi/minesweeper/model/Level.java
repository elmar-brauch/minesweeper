package de.bsi.minesweeper.model;

public enum Level {
	EASY(5, 5, 4),
	MEDIUM(10, 12, 25),
	HARD(25, 20, 125);
	
	private int columns;
	private int rows;
	private int mines;
	
	private Level(int columns, int rows, int mines) {
		this.columns = columns;
		this.rows = rows;
		this.mines = mines;
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	public int getMines() {
		return mines;
	}
}

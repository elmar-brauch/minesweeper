package de.bsi.minesweeper.model;

import lombok.Data;

@Data
public class Cell {
	
	private final int inRow;
	private final int inColumn;
	private CellStatus status = CellStatus.AWAY_OF_MINES;
	private long score = 0;
	private boolean open = false;
	
	public Cell(int inRow, int inColumn) {
		this.inRow = inRow;
		this.inColumn = inColumn;
	}

	public void changeScoreAndStatus(long newScore) {
		this.score = newScore;
		if (!isMine())
			this.status = newScore == 0 ? CellStatus.AWAY_OF_MINES : CellStatus.NEXT_TO_MINES;
	}
	
	public void placeMine() {
		this.status = CellStatus.MINE;
	}
	
	public boolean isMine() {
		return this.status == CellStatus.MINE;
	}
	
	public String getPosition() {
		return inRow + ":" + inColumn;
	}
	
	@Override
	public String toString() {
		if (!open) 
			return "?";
		if (isMine())
			return "X";
		return "" + score;
	}
	
}

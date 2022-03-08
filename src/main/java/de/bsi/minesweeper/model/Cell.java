package de.bsi.minesweeper.model;

public class Cell {
	
	private final Position position;
	private CellStatus status = CellStatus.AWAY_OF_MINES;
	private long score = 0;
	private boolean open = false;
	
	public Cell(Position position) {
		this.position = position;
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
	
	@Override
	public String toString() {
		if (!open) 
			return "?";
		if (isMine())
			return "X";
		return "" + score;
	}

	public void open() {
		this.open = true;
	}
	
	public Position getPosition() {
		return position;
	}

	public long getScore() {
		return score;
	}

	public boolean isOpen() {
		return open;
	}

	public CellStatus getStatus() {
		return status;
	}
	
}

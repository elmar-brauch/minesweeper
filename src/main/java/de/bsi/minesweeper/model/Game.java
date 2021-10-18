package de.bsi.minesweeper.model;

import lombok.Data;

@Data
public class Game {
	
	private MineField field;
	private Level level;
	private GameStatus status;
	private String clickedCellPosition;
	
	public Game(Level level) {
		this.field = new MineField(level.getColumns(), level.getRows());
		this.field.placeMineRandomly(level.getMines());
		this.level = level;
		this.status = GameStatus.ONGOING;
	}
	
	public GameStatus playRound(int row, int column) {
		if (field.isCellClosed(row, column)) {
			var cell = field.openCell(row, column);
			updateStatus(cell);
		}
		return status;
	}
	
	private void updateStatus(Cell openedCell) {
		if (openedCell.isMine()) {
			status = GameStatus.LOSE;
			field.openAllCells();
		}
		else if (field.isEveryFreeCellOpen()) {
			status = GameStatus.WIN;
			field.openAllCells();
		}	
		else
			status = GameStatus.ONGOING;	
	}

}

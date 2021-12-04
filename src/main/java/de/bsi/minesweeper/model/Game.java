package de.bsi.minesweeper.model;

import lombok.Getter;

public class Game {
	
	@Getter MineField field;
	@Getter private GameStatus status;
	
	public Game(Level level) {
		this.field = new MineField(level.getRows(), level.getColumns());
		this.field.placeMinesRandomly(level.getMines());
		this.status = GameStatus.ONGOING;
	}
	
	public GameStatus playRound(Position position) {
		if (!field.getCellInFieldAtPosition(position).orElseThrow().isOpen()) {
			var cell = field.openCell(position);
			updateStatus(cell);
		}
		return status;
	}
	
	private void updateStatus(Cell openedCell) {
		if (openedCell.isMine())
			endGame(GameStatus.LOSE);
		else if (field.isEveryFreeCellOpen())
			endGame(GameStatus.WIN);
		else
			status = GameStatus.ONGOING;	
	}

	private void endGame(GameStatus finalStatus) {
		status = finalStatus;
		field.openAllCells();
	}

}

package de.bsi.minesweeper.model;

import lombok.Getter;

public class Game {
	
	@Getter MineField field;
	@Getter private GameStatus status;
	
	public Game(Level level) {
		this(level.getRows(), level.getColumns());
		this.field.placeMinesRandomly(level.getMines());
	}
	
	public Game(int numberOfRows, int numberOfColumns) {
		this.field = new MineField(numberOfRows, numberOfColumns);
		this.status = GameStatus.ONGOING;		
	}
	
	public GameStatus playRound(Position position) {
		var optCell = field.getCellAtPosition(position); 
		if (optCell.isPresent() && !optCell.get().isOpen())
			openCellAndCheckEndOfGame(position);
		return status;
	}
	
	private void openCellAndCheckEndOfGame(Position position) {
		if (field.openCell(position).isMine())
			endGame(GameStatus.LOSE);
		else if (field.isEveryFreeCellOpen())
			endGame(GameStatus.WIN);	
	}

	private void endGame(GameStatus finalStatus) {
		status = finalStatus;
		field.openAllCells();
	}

}

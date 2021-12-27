package de.bsi.minesweeper.model;

import java.util.List;

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
		if (field.openCell(position).isMine()) {
			status = GameStatus.LOSE;
			changeAllCellsToOpen(field, true);	
		} else if (field.isEveryFreeCellOpen()) {
			status = GameStatus.WIN;
			changeAllCellsToOpen(field, true);	
		} else {
			status = GameStatus.ONGOING;	
		}
	}
	
	void changeAllCellsToOpen(MineField field, boolean isOpen) {
		var allRowsWithCells = field.getFieldRows();
		var allCellsStream = allRowsWithCells.stream().flatMap(List<Cell>::stream); 
		allCellsStream.forEach(cell -> cell.setOpen(isOpen));
	}

}

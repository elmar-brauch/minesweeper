package de.bsi.minesweeper.model;

public class Game {
	
	MineField field;
	private GameStatus status;
	
	public Game(Level level) {
		this(level.getRows(), level.getColumns());
		this.field.placeMinesRandomly(level.getMines());
	}
	
	// Constructor only for TDD exercise.
	public Game(Level level, GameStatus fakeStatusForTesting) {
		this(level.getRows(), level.getColumns());
		this.field.placeMinesRandomly(level.getMines());
		this.status = fakeStatusForTesting;
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

	public MineField getField() {
		return field;
	}

	public GameStatus getStatus() {
		return status;
	}
}

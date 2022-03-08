package de.bsi.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

public class MineField {
	
	private final List<List<Cell>> fieldRows = new ArrayList<>();
	
	public MineField(int numberOfRows, int numberOfColumns) {
		for (int rowNumnber = 0; rowNumnber < numberOfRows; rowNumnber++)
			fieldRows.add(createNewRow(rowNumnber, numberOfColumns));
	}
	
	private List<Cell> createNewRow(int rowNumber, int numberOfColumnsInRow) {
		var newRow = new ArrayList<Cell>();
		for (int columnNumber = 0; columnNumber < numberOfColumnsInRow; columnNumber++)
			newRow.add(new Cell(new Position(rowNumber, columnNumber)));
		return newRow;
	}
	
	private final RandomGenerator random = RandomGenerator.LeapableGenerator.of("Xoroshiro128PlusPlus");
	
	public void placeMinesRandomly(int mines) {
		var freeCells = new ArrayList<>(getFreeCells());
		for (int i = 0; i < mines && !freeCells.isEmpty(); i++) {
			int pos = random.nextInt(0, freeCells.size());
			var cellWithMine = freeCells.remove(pos);
			placeOneMineAndUpdateScoreOfNeighbours(cellWithMine.getPosition());
		}
	}
	
	public void placeOneMineAndUpdateScoreOfNeighbours(Position position) {
		getCellAtPosition(position).ifPresent(Cell::placeMine);
		getNeighbourCells(position).forEach(cell -> cell.changeScoreAndStatus(cell.getScore() + 1));
	}
	
	public Optional<Cell> getCellAtPosition(Position position) {
		int row = position.row();
		int column = position.column();
		if (isInField(row, column))
			return Optional.of(fieldRows.get(row).get(column)); 
		return Optional.empty();
	}
	
	public Cell openCell(Position position) {
		var cell = getCellAtPosition(position).orElseThrow(); 
		cell.open();
		if (CellStatus.AWAY_OF_MINES.equals(cell.getStatus()))
			getNeighbourCells(position).stream()
					.filter(c -> !c.isOpen())
					.map(Cell::getPosition)
					.forEach(this::openCell);
		return cell; 
	}
	
	public void openAllCells() {
		fieldRows.stream().flatMap(List<Cell>::stream).forEach(Cell::open);
	}
	
	public boolean isEveryFreeCellOpen() {
		return getFreeCells().stream().allMatch(Cell::isOpen);
	}
	
	private List<Cell> getFreeCells() {
		return fieldRows.stream()
				.flatMap(List<Cell>::stream)
				.filter(cell -> !cell.isMine())
				.toList(); 
	}
	
	List<Cell> getNeighbourCells(Position position) {
		if (getCellAtPosition(position).isEmpty())
			return List.of();
		return positionsOfEightNeighbours(position.row(), position.column())
				.stream()
				.map(this::getCellAtPosition)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.toList();
	}
	
	private List<Position> positionsOfEightNeighbours(int row, int column) {
		return List.of(
				new Position(row-1	, column-1	),
				new Position(row-1	, column	),
				new Position(row-1	, column+1	),
				new Position(row	, column-1	),
				new Position(row	, column+1	),
				new Position(row+1	, column-1	),
				new Position(row+1	, column	),
				new Position(row+1	, column+1	));
	}
	
	private boolean isInField(int row, int column) {
		return row >= 0 && row < fieldRows.size()
				&& column >= 0 && column < fieldRows.get(row).size();
	}

	public List<List<Cell>> getFieldRows() {
		return fieldRows;
	}
}

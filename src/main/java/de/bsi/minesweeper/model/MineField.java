package de.bsi.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

import lombok.Getter;

public class MineField {
	
	@Getter private final List<List<Cell>> fieldRows = new ArrayList<>();
	
	public MineField(int numberOfRows, int numberOfColumns) {
		for (int rowNumnber = 0; rowNumnber < numberOfRows; rowNumnber++)
			fieldRows.add(createNewRow(rowNumnber, numberOfColumns));
	}
	
	private List<Cell> createNewRow(int rowNumber, int numberOfColumnsInRow) {
		var newRow = new ArrayList<Cell>();
		for (int columnNumber = 0; columnNumber < numberOfColumnsInRow; columnNumber++)
			newRow.add(new Cell(Position.of(rowNumber, columnNumber)));
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
		int row = position.getRow();
		int column = position.getColumn();
		if (isInField(row, column))
			return Optional.of(fieldRows.get(row).get(column)); 
		return Optional.empty();
	}
	
	public Cell openCell(Position position) {
		var cell = getCellAtPosition(position).orElseThrow(); 
		cell.setOpen(true);
		if (CellStatus.AWAY_OF_MINES.equals(cell.getStatus()))
			getNeighbourCells(position).stream()
					.filter(c -> !c.isOpen())
					.map(Cell::getPosition)
					.forEach(this::openCell);
		return cell; 
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
		var neighbours = new ArrayList<Cell>();
		int row = position.getRow();
		int col = position.getColumn();
		if (isInField(row, col)) {
			boolean notFirstRow = row > 0;
			boolean notLastRow = row + 1 < fieldRows.size();
			boolean notFirstColumn = col > 0;
			boolean notLastColumn = col+1 < fieldRows.get(row).size();
			if (notFirstRow && notFirstColumn) neighbours.add(fieldRows.get(row-1).get(col-1));
			if (notFirstRow) neighbours.add(fieldRows.get(row-1).get(col));
			if (notFirstRow && notLastColumn) neighbours.add(fieldRows.get(row-1).get(col+1));
			if (notFirstColumn) neighbours.add(fieldRows.get(row).get(col-1));
			if (notLastColumn) neighbours.add(fieldRows.get(row).get(col+1));
			if (notLastRow && notFirstColumn) neighbours.add(fieldRows.get(row+1).get(col-1));
			if (notLastRow) neighbours.add(fieldRows.get(row+1).get(col));
			if (notLastRow && notLastColumn) neighbours.add(fieldRows.get(row+1).get(col+1));
		}
		return neighbours;
	}
	
	private boolean isInField(int row, int column) {
		return row >= 0 && row < fieldRows.size()
				&& column >= 0 && column < fieldRows.get(row).size();
	}

}

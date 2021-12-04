package de.bsi.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class MineField {
	
	private final List<List<Cell>> fieldRows = new ArrayList<>();
	private final RandomGenerator random = RandomGenerator.LeapableGenerator.of("Xoroshiro128PlusPlus");
	
	public MineField(int numberOfRows, int numberOfColumns) {
		for (int rowNumnber = 0; rowNumnber < numberOfRows; rowNumnber++)
			fieldRows.add(createNewRow(rowNumnber, numberOfColumns));
	}
	
	private List<Cell> createNewRow(int rowNumber, int numberOfColumnsInRow) {
		var newRow = new ArrayList<Cell>();
		for (int columnNumber = 0; columnNumber < numberOfColumnsInRow; columnNumber++)
			newRow.add(new Cell(rowNumber, columnNumber));
		return newRow;
	}
	
	public void placeMinesRandomly(int mines) {
		var freeCells = getFreeCells();
		for (int i = 0; i < mines && !freeCells.isEmpty(); i++) {
			int pos = random.nextInt(0, freeCells.size() - 1);
			var cellWithMine = freeCells.remove(pos);
			placeOneMineAndUpdateScoreOfNeighbours(cellWithMine.getInColumn(), cellWithMine.getInRow());
		}
	}
	
	void placeOneMineAndUpdateScoreOfNeighbours(int column, int row) {
		getCellInFieldAtPosition(row, column).ifPresent(Cell::placeMine);
		getNeighbourCells(row, column).forEach(cell -> cell.changeScoreAndStatus(cell.getScore() + 1));
	}
	
	public Cell openCell(int row, int column) {
		var cell = getCellInFieldAtPosition(row, column).orElseThrow(); 
		cell.setOpen(true);
		if (CellStatus.AWAY_OF_MINES.equals(cell.getStatus()))
			getNeighbourCells(row, column).stream()
					.filter(c -> !c.isOpen())
					.forEach(c -> openCell(c.getInRow(), c.getInColumn()));
		return cell; 
	}
	
	public void openAllCells() {
		fieldRows.stream().flatMap(List<Cell>::stream).forEach(cell -> cell.setOpen(true));
	}
	
	public boolean isEveryFreeCellOpen() {
		return getFreeCells().stream().allMatch(Cell::isOpen);
	}
	
	public boolean isCellClosed(int row, int column) {
		return !getCellInFieldAtPosition(row, column).orElseThrow().isOpen();
	}
	
	private List<Cell> getFreeCells() {
		return fieldRows.stream()
				.flatMap(List<Cell>::stream)
				.filter(cell -> !cell.isMine())
				.collect(Collectors.toList()); //NOSONAR because mutable list is required. 
	}
	
	List<Cell> getNeighbourCells(int row, int column) {
		if (getCellInFieldAtPosition(row, column).isEmpty())
			return List.of();
		return positionsOfEightNeighbours(row, column).stream()
				.map(position -> getCellInFieldAtPosition(position[0], position[1]))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.toList();
	}
	
	private List<int[]> positionsOfEightNeighbours(int row, int column) {
		return List.of(
				new int[]{row-1	, column-1},
				new int[]{row-1	, column},
				new int[]{row-1	, column+1},
				new int[]{row	, column-1},
				new int[]{row	, column+1},
				new int[]{row+1	, column-1},
				new int[]{row+1	, column},
				new int[]{row+1	, column+1});
	}
	
	private Optional<Cell> getCellInFieldAtPosition(int row, int column) {
		if (row >= 0 && row < fieldRows.size()
				&& column >= 0 && column < fieldRows.get(row).size())
			return Optional.of(fieldRows.get(row).get(column)); 
		return Optional.empty();
	}

}

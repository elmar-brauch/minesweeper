package de.bsi.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class MineField {
	
	private List<List<Cell>> fieldRows = new ArrayList<>();
	private RandomGenerator random = RandomGenerator.LeapableGenerator.of("Xoroshiro128PlusPlus");
	
	public MineField(int rows, int columns) {
		for (int y = 0; y < rows; y++) {
			var row = new ArrayList<Cell>();
			fieldRows.add(row);
			for (int x = 0; x < columns; x++) {
				row.add(new Cell(y, x));
			}
		}
	}
	
	public void placeMineRandomly(int mines) {
		var freeCells = getFreeCells();
		for (int i = 0; i < mines && !freeCells.isEmpty(); i++) {
			int pos = random.nextInt(0, freeCells.size() - 1);
			var cellWithMine = freeCells.remove(pos);
			placeOneMineAndUpdateScoreOfNeighbours(cellWithMine.getInColumn(), cellWithMine.getInRow());
		}
	}
	
	void placeOneMineAndUpdateScoreOfNeighbours(int column, int row) {
		fieldRows.get(row).get(column).placeMine();
		getNeighbourCells(row, column).forEach(cell -> cell.changeScoreAndStatus(cell.getScore() + 1));
	}
	
	public Cell openCell(int row, int col) {
		var cell = fieldRows.get(row).get(col); 
		cell.setOpen(true);
		if (CellStatus.AWAY_OF_MINES.equals(cell.getStatus()))
			getNeighbourCells(row, col).stream()
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
		return !fieldRows.get(row).get(column).isOpen();
	}
	
	private List<Cell> getFreeCells() {
		return fieldRows.stream()
				.flatMap(List<Cell>::stream)
				.filter(cell -> !cell.isMine())
				.collect(Collectors.toList()); //NOSONAR because mutable list is required. 
	}
	
	private boolean isInField(int row, int col) {
		return row >= 0 && row < fieldRows.size() 
				&& col >= 0 && col < fieldRows.get(row).size();
	}
	
	List<Cell> getNeighbourCells(int row, int col) {
		var neighbours = new ArrayList<Cell>();
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

}

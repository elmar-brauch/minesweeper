package de.bsi.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	public Optional<Cell> getCellAtPosition(Position position) {
		int row = position.row();
		int column = position.column();
		if (row >= 0 && row < fieldRows.size()
				&& column >= 0 && column < fieldRows.get(row).size())
			return Optional.of(fieldRows.get(row).get(column)); 
		return Optional.empty();
	}
	
	List<Cell> getNeighbourCells(Position position) {
		// TODO Implement this method. 
		// Feel free to create helping methods.
		// Use MineFieldTest to test it!
		return List.of();
	}

}

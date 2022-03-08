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
	
	// Solution comment:
	// The if-statement deals with all positions, which are outside the field,
	// so that the 2nd return statement can focus on positions inside the field.
	//
	// To get only neighbour cells, which are inside the field Stream operations are used.
	// map(this::getCellAtPosition) => translates the Position into an Optional<Cell>.
	//
	// If the position is not in the field, getCellAtPosition returns an empty Optional, so:
	// filter(Optional::isPresent) => removes all empty Optionals from the stream.
	//
	// If the position is in the field, getCellAtPosition returns an Optional containing the neighbour Cell, so:
	// map(Optional::get) => translates the Optional<Cell> into the Cell.
	//
	// toList() terminates the Stream and collects all Cells in a List, which reached the end of the Stream.
	//
	// The stream-handling could be extracted into a separate method.
	// In a separate method a solution with a loop instead of a stream could be also possible.
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
	
	// Solution comment:
	// This method is simple and stupid, which is good for having Clean Code.
	// It returns always the 8 cells around the current position.
	// It does not care, if the position is in the field or not.
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

}

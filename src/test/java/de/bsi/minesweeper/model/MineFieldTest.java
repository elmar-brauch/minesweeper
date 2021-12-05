package de.bsi.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MineFieldTest {

	private MineField field;
	
	@BeforeEach
	void setup() {
		field = new MineField(3, 4);
	}
	
	// TODO Write JUnit test to test your implementation of getNeighbourCells.
	// The following cases should be covered:
	// * Cell in the corner of the field has 3 neighbour cells
	// * Cell at the edge of the field has 5 neighbour cells
	// * Cell in the middle of the field has 8 neighbour cells
	// * Any cell outside the field has 0 neighbour cells. It is specified like this.
	@Test
	void getNeighbourCells() {
		var neighbours = field.getNeighbourCells(Position.of(1, 1));
		assertEquals(8, neighbours.size());
	}
	
	@ParameterizedTest
	@CsvSource({"true,0,0","true,2,3",
		"false,-1,0", "false,3,3", "false,2,4", "false,10,20"})
	void getCellAtPosition(boolean expectedPresent, int row, int column) {
		var optCell = field.getCellAtPosition(Position.of(row, column));
		assertEquals(expectedPresent, optCell.isPresent());
	}

}

package de.bsi.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
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
	//
	// Solution comment:
	// Testing the cells at all possible positions in the field can be done easily with a parameterized test.
	// Any cell can be defined by its position, 
	// so you can pass it together with the expected result as parameters to the test.
	// The lines in the csv source definition match the cases to be tested.
	@ParameterizedTest
	@CsvSource({"3,0,0","3,2,0","3,0,3","3,2,3",
		"5,1,0","5,2,1","5,2,2","5,1,3","5,0,2","5,0,1",
		"8,1,1","8,1,2",
		"0,-1,2","0,3,0","0,2,-12345","0,0,4"})
	void getNeighbourCells(int expectedNumberOfNeighbours, int row, int column) {
		var neighbours = field.getNeighbourCells(new Position(row, column));
		assertEquals(expectedNumberOfNeighbours, neighbours.size());
	}
	
	@ParameterizedTest
	@CsvSource({"true,0,0","true,2,3",
		"false,-1,0", "false,3,3", "false,2,4", "false,10,20"})
	void getCellAtPosition(boolean expectedPresent, int row, int column) {
		var optCell = field.getCellAtPosition(new Position(row, column));
		assertEquals(expectedPresent, optCell.isPresent());
	}

}

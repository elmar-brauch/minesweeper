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
	
	@ParameterizedTest
	@CsvSource({"0,-1,2","0,3,0","0,2,-12345","0,0,4",
		"3,0,0","3,2,0","3,0,3","3,2,3",
		"5,1,0","5,2,1","5,2,2","5,1,3","5,0,2","5,0,1",
		"8,1,1","8,1,2"})
	void getNeighbourCells(int expectedNumberOfNeighbours, int row, int column) {
		assertEquals(expectedNumberOfNeighbours, field.getNeighbourCells(row, column).size());
	}
	
	void openField() {
		
	}

}

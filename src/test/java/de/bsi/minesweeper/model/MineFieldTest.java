package de.bsi.minesweeper.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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
		var neighbours = field.getNeighbourCells(Position.of(row, column));
		assertEquals(expectedNumberOfNeighbours, neighbours.size());
	}
	
	@ParameterizedTest
	@CsvSource({"true,0,0","true,2,3",
		"false,-1,0", "false,3,3", "false,2,4", "false,10,20"})
	void getCellInFieldAtPosition(boolean expectedPresent, int row, int column) {
		var optCell = field.getCellInFieldAtPosition(Position.of(row, column));
		assertEquals(expectedPresent, optCell.isPresent());
	}
	
	@Test
	void openAllCellsInField() {
		assertTrue(allCellsInField().allMatch(c -> !c.isOpen()));
		field.openAllCells();
		assertTrue(allCellsInField().allMatch(c -> c.isOpen()));
	}
	
	@Test
	void openSingleCellWithoutMines() {
		var testPosition = Position.of(1, 1);
		assertFalse(field.getCellInFieldAtPosition(testPosition).orElseThrow().isOpen());
		assertTrue(field.openCell(testPosition).isOpen());
		assertTrue(field.isEveryFreeCellOpen());
	}
	
	@Test
	void openSingleCellNextToMine() {
		field.placeOneMineAndUpdateScoreOfNeighbours(Position.of(0, 1));
		assertTrue(field.openCell(Position.of(1, 1)).isOpen());
		assertEquals(11, allCellsInField().filter(cell -> !cell.isOpen()).count());
	}
	
	@Test
	void openSingleCellAwayOfMine() {
		field.placeOneMineAndUpdateScoreOfNeighbours(Position.of(0, 1));
		assertTrue(field.openCell(Position.of(2, 3)).isOpen());
		assertEquals(2, allCellsInField().filter(cell -> !cell.isOpen()).count());
	}
	
	@Test
	void isEveryFreeCellOpenPositive() {
		allCellsInField().forEach(Cell::open);
		assertTrue(field.isEveryFreeCellOpen());
	}
	
	@Test
	void isEveryFreeCellOpenNegative() {
		assertFalse(field.isEveryFreeCellOpen());
		allCellsInField().limit(11).forEach(Cell::open);
		assertFalse(field.isEveryFreeCellOpen());
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 5, 12})
	void placeMinesRandomly(int mines) {
		field.placeMinesRandomly(mines);
		assertEquals(mines, allCellsInField().filter(Cell::isMine).count());
	}
	
	@Test
	void placeMinesRandomlyEdgeCases() {
		field.placeMinesRandomly(-1);
		assertEquals(0, allCellsInField().filter(Cell::isMine).count());
		
		field.placeMinesRandomly(13);
		assertEquals(12, allCellsInField().filter(Cell::isMine).count());
	}
	
	private Stream<Cell> allCellsInField() {
		return field.getFieldRows().stream().flatMap(row -> row.stream());
	}

}

package de.bsi.minesweeper.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CellTest {
	
	private Cell testCell = new Cell(new Position(1, 2));

	@Test
	void placeMine() {
		assertFalse(testCell.isMine());
		testCell.placeMine();
		assertTrue(testCell.isMine());
	}
	
	@ParameterizedTest
	@CsvSource({"AWAY_OF_MINES,0", "NEXT_TO_MINES,1", "NEXT_TO_MINES,8"})
	void changeScoreAndStatus(CellStatus expectedStatus, long score) {
		testCell.changeScoreAndStatus(score);
		assertEquals(expectedStatus, testCell.getStatus());
	}
	
	@ParameterizedTest
	@ValueSource(longs = {0, 1, 1000})
	void changeScoreAndStatusForMine(long score) {
		testCell.placeMine();
		testCell.changeScoreAndStatus(score);
		assertEquals(CellStatus.MINE, testCell.getStatus());
	}

}

package de.bsi.minesweeper.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PositionTest {

	@ParameterizedTest
	@CsvSource({"0,1,0:1","123456789,987654321,123456789:987654321"})
	void parsePositive(int row, int column, String position) {
		assertEquals(new Position(row, column), Position.parse(position));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"a:1", "1l:0", "11", " 1:1", "0 : 0"})
	@NullAndEmptySource
	void parseNegative(String position) {
		assertThrows(IllegalArgumentException.class, () -> Position.parse(position));
	}
	
	@Test
	void toStringTest() {
		assertEquals("123:456", new Position(123, 456).toString());
	}

}

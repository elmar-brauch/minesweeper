package de.bsi.minesweeper.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
	
	private static final Position MINE_POSITION = Position.of(1, 1);

	private Game game;
	
	@BeforeEach
	void setup() {
		game = new Game(Level.EASY);
		game.field = new MineField(2, 2);
		game.field.placeOneMineAndUpdateScoreOfNeighbours(MINE_POSITION);
	}
	
	@Test
	void playRoundAndLose() {
		var status = game.playRound(MINE_POSITION);
		assertEquals(GameStatus.LOSE, status);
		verifyAllCellsOpen();
	}
	
	@Test
	void playTwoRounds() {
		assertEquals(GameStatus.ONGOING, game.playRound(Position.of(1, 0)));
		assertEquals(GameStatus.ONGOING, game.playRound(Position.of(0, 1)));
	}
	
	@Test
	void playThreeRoundsAndWin() {
		game.playRound(Position.of(1, 0));
		game.playRound(Position.of(0, 0));
		
		var status = game.playRound(Position.of(0, 1));
		assertEquals(GameStatus.WIN, status);
		verifyAllCellsOpen();
	}
	
	private void verifyAllCellsOpen() {
		assertTrue(game.field.getFieldRows().stream().flatMap(List<Cell>::stream).allMatch(Cell::isOpen));
	}

}

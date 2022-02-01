package de.bsi.minesweeper.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.bsi.minesweeper.model.Game;
import de.bsi.minesweeper.model.GameStatus;
import de.bsi.minesweeper.model.Level;

class StatisticServiceTest {

	private static final String PLAYER_1 = "Kamil Muranski";
	private static final String PLAYER_2 = "Ze-En Ju";
	
	private StatisticService stats = new StatisticService();
	
	// TODO Implement the following requirements in TDD style! 
	// Implement tests & methods to answer following questions:
	// * How many ongoing games exist?
	// * Which player has most ongoing games?
	// * Which player has most wins?
	// * Which player has most loss?
	// * Top 3 players with best wins/games ratio
	
	@Test
	void ongoingGamesCount() {
		startGamesFor(3, PLAYER_1, GameStatus.ONGOING);
		startGamesFor(3, PLAYER_1, GameStatus.LOSE);
		assertEquals(3, stats.ongoingGamesCount());
		startGamesFor(7, PLAYER_2, GameStatus.ONGOING);
		assertEquals(10, stats.ongoingGamesCount());
	}
	
	@Test
	void getPlayerWithMostOngoingGames() {
		assertTrue(stats.getPlayerWithMostGamesIn(GameStatus.ONGOING).isEmpty());
		startGamesFor(3, PLAYER_1, GameStatus.ONGOING);
		startGamesFor(2, PLAYER_1, GameStatus.LOSE);
		startGamesFor(4, PLAYER_2, GameStatus.ONGOING);
		assertEquals(PLAYER_2, stats.getPlayerWithMostGamesIn(GameStatus.ONGOING).get());
	}
	
	@Test
	void getPlayerWithMostLostGames() {
		startGamesFor(2, PLAYER_1, GameStatus.LOSE);
		assertEquals(PLAYER_1, stats.getPlayerWithMostGamesIn(GameStatus.LOSE).get());
	}
	
	@Test
	void getPlayerWithMostWinGames() {
		startGamesFor(2, PLAYER_1, GameStatus.WIN);
		startGamesFor(3, PLAYER_2, GameStatus.WIN);
		assertEquals(PLAYER_2, stats.getPlayerWithMostGamesIn(GameStatus.WIN).get());
		startGamesFor(2, PLAYER_1, GameStatus.WIN);
		assertEquals(PLAYER_1, stats.getPlayerWithMostGamesIn(GameStatus.WIN).get());
	}
	
	@Test
	void getPlayerWithMostGamesInExceptionTest() {
		startGamesFor(2, PLAYER_1, GameStatus.LOSE);
		assertThrows(IllegalArgumentException.class, () -> stats.getPlayerWithMostGamesIn(null)); 
	}
	
	@Test
	void calculateWinRatioForPlayer() {
		assertEquals(0, stats.calculateWinRatioFor(PLAYER_1));
		
		startGamesFor(2, PLAYER_1, GameStatus.WIN);
		assertEquals(1, stats.calculateWinRatioFor(PLAYER_1));
		
		startGamesFor(2, PLAYER_1, GameStatus.LOSE);
		assertEquals(0.5, stats.calculateWinRatioFor(PLAYER_1));
		
		startGamesFor(20, PLAYER_2, GameStatus.LOSE);
		startGamesFor(2, PLAYER_1, GameStatus.ONGOING);
		startGamesFor(2, PLAYER_1, GameStatus.LOSE);
		assertEquals(0.25, stats.calculateWinRatioFor(PLAYER_1));
	}
	
	@Test
	void top3Players() {
		String playerX = "X";
		String playerY = "Y";
		
		startGamesFor(1, playerY, GameStatus.LOSE);
		assertEquals(List.of(playerY), stats.top3Players());
		
		startGamesFor(100, PLAYER_1, GameStatus.WIN);
		startGamesFor(10, PLAYER_1, GameStatus.LOSE);
		
		startGamesFor(1, PLAYER_2, GameStatus.WIN);
		
		assertEquals(List.of(PLAYER_2, PLAYER_1, playerY), stats.top3Players());
		
		startGamesFor(200, playerX, GameStatus.WIN);
		startGamesFor(25, playerX, GameStatus.LOSE);
		
		assertEquals(List.of(PLAYER_2, PLAYER_1, playerX), stats.top3Players());
	}
	
	@Test
	void top3PlayersSameWinRatio() {
		String playerX = "X";
		
		startGamesFor(1, PLAYER_1, GameStatus.WIN);
		startGamesFor(1, PLAYER_2, GameStatus.WIN);
		startGamesFor(1, playerX, GameStatus.WIN);
		
		var topList = stats.top3Players();
		assertTrue(topList.contains(playerX) && topList.contains(PLAYER_1) && topList.contains(PLAYER_2));
	}
	
	private void startGamesFor(int games, String player, GameStatus status) {
		for (int i = 0; i < games; i++)
			stats.addGame(player, new Game(Level.EASY, status));
	}

}

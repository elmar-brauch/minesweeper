package de.bsi.minesweeper.service;

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
		startGamesFor(7, PLAYER_2, GameStatus.ONGOING);
		// TODO Uncomment next line and start doing TDD.
		// assertEquals(10, stats.ongoingGamesCount());
	}
	
	private void startGamesFor(int games, String player, GameStatus status) {
		for (int i = 0; i < games; i++)
			stats.addGame(player, new Game(Level.EASY, status));
	}

}

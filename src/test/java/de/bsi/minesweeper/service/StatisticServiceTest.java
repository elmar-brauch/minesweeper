package de.bsi.minesweeper.service;

import org.junit.jupiter.api.Test;

import de.bsi.minesweeper.model.Game;
import de.bsi.minesweeper.model.Level;

class StatisticServiceTest {

	private static final String PLAYER_1 = "Kamil Muranski";
	private static final String PLAYER_2 = "Ze-En Ju";
	
	private StatisticService stats = new StatisticService();
	
	@Test
	void startedGamesCount() {
		startGamesFor(3, PLAYER_1);
		startGamesFor(7, PLAYER_2);
		// TODO Uncomment next line and start doing TDD.
		// assertEquals(stats.startedGamesCount());
	}
	
	// TODO Implement the following requirements in TDD style! 
	// Move all finished games to playersCompletedGames map.
	// Implement methods to answer following questions:
	// * Which player has most wins
	// * Which player has most loss
	// * Which player has most ongoing games
	// * Top 3 players with best wins/loss ratio
	// * Top n players with best wins/loss ratio per level
	
	private void startGamesFor(int games, String player) {
		for (int i = 0; i < games; i++)
			stats.addStartedGame(player, new Game(Level.EASY));
	}

}

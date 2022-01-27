package de.bsi.minesweeper.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import de.bsi.minesweeper.model.Game;

@Service
public class StatisticService {
	
	private Map<Game, String> gamesStartedByPlayers = new HashMap<>();
	private Map<String, List<PlayedGame>> playersCompletedGames = new HashMap<>();
	
	public void addStartedGame(String playerName, Game game) {
		gamesStartedByPlayers.put(game, playerName);
	}

}

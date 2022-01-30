package de.bsi.minesweeper.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import de.bsi.minesweeper.model.Game;

@Service
public class StatisticService {
	
	private Map<Game, String> game2PlayerMap = new HashMap<>();
	
	public void addGame(String playerName, Game game) {
		game2PlayerMap.put(game, playerName);
	}

}

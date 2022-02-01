package de.bsi.minesweeper.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.bsi.minesweeper.model.Game;
import de.bsi.minesweeper.model.GameStatus;

@Service
public class StatisticService {
	
	private Map<Game, String> game2PlayerMap = new HashMap<>();
	
	public void addGame(String playerName, Game game) {
		game2PlayerMap.put(game, playerName);
	}

	public long ongoingGamesCount() {
		return game2PlayerMap.keySet().stream()
				.filter(g -> GameStatus.ONGOING.equals(g.getStatus()))
				.count();
	}

	public Optional<String> getPlayerWithMostGamesIn(GameStatus status) {
		if (status == null)
			throw new IllegalArgumentException("status must not be null");
		var groups = game2PlayerMap.entrySet().stream()
				.filter(e -> status.equals(e.getKey().getStatus()))
				.collect(Collectors.groupingBy(Entry::getValue));
		return groups.entrySet().stream()
				.max((e1, e2) -> e1.getValue().size() - e2.getValue().size())
				.map(Entry::getKey);
	}

	double calculateWinRatioFor(String playerName) {
		long wins = countGamesBy(playerName, GameStatus.WIN);
		long total = wins + countGamesBy(playerName, GameStatus.LOSE) 
				+ countGamesBy(playerName, GameStatus.ONGOING);
		if (total <= 0)
			return 0.0d;
		return ((double) wins) / total;
	}

	private long countGamesBy(String playerName, GameStatus status) {
		return game2PlayerMap.entrySet().stream()
				.filter(entry -> status.equals(entry.getKey().getStatus())
						&& playerName.equals(entry.getValue()))
				.count();
	}

	private Comparator<Map.Entry<String, List<Map.Entry<Game, String>>>> winRatioComparator = (e1, e2) -> {
		double p1WinRatio = calculateWinRatioFor(e1.getKey());
		double p2WinRatio = calculateWinRatioFor(e2.getKey());
		if (p1WinRatio == p2WinRatio)
			return 0;
		if (p1WinRatio > p2WinRatio)
			return -1;
		return 1;
	};
	
	public List<String> top3Players() {
		return game2PlayerMap.entrySet().stream()
				.collect(Collectors.groupingBy(Entry::getValue))
				.entrySet().stream()
				.sorted(winRatioComparator)
				.limit(3)
				.map(Entry::getKey)
				.toList();
	}

}

package de.bsi.minesweeper.service;

import java.time.Instant;

import de.bsi.minesweeper.model.GameStatus;
import de.bsi.minesweeper.model.Level;
import lombok.Data;

@Data
public class PlayedGame {
	
	private String playerName;
	private Level level;
	private GameStatus status;
	private final Instant creationTimestamp = Instant.now();

}

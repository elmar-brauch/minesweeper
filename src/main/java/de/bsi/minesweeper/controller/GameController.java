package de.bsi.minesweeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;

import de.bsi.minesweeper.model.Game;
import de.bsi.minesweeper.model.Level;
import de.bsi.minesweeper.model.Position;
import de.bsi.minesweeper.service.StatisticService;
import lombok.extern.slf4j.Slf4j;

@Controller
@SessionScope
@RequestMapping("/")
@Slf4j
public class GameController {
	
	private static final String VIEW_MINESWEEPER = "minesweeper";
	private static final String VIEW_LOGIN = "login";
	
	@Autowired private StatisticService stats;

    private Game game = new Game(Level.EASY);
    private String playerName;

    @GetMapping
    public String startPage(Model model) {
    	return VIEW_LOGIN;
    }
    
    @PostMapping("login")
    public String startGame(Model model, @RequestParam String player) {
    	log.info("Player {} started the game.", player);
    	playerName = player;
    	createGameWithStatistics(Level.EASY);
    	return getGameViewAndUpdateModel(model);
    }
    
    @PostMapping("play")
    public String openCellToPlayOneRound(Model model, @RequestParam String position) {
    	log.info("Player {} opens cell {}.", playerName, position);
        game.playRound(Position.parse(position));        		
        return getGameViewAndUpdateModel(model);
    }
    
    @PostMapping("restart")
    public String restartGame(Model model, @RequestParam String level) {
    	log.info("Player {} restarted game in level {}.", playerName, level);
    	createGameWithStatistics(Level.valueOf(level));
    	return getGameViewAndUpdateModel(model);
    }
    
    @GetMapping("restart")
	public String createEmptyGame(Model model, @RequestParam int rows, @RequestParam int columns) {
		log.info("New Game without mines created in size {}x{}", rows, columns);
		game = new Game(rows, columns);
		return getGameViewAndUpdateModel(model);
	}
	
	@GetMapping("mine")
	public String placeMineAt(Model model, @RequestParam int row, @RequestParam int column) {
		log.info("Place mine at position {}:{}", row, column);
		var minePosition = Position.of(row, column);
		game.getField().placeOneMineAndUpdateScoreOfNeighbours(minePosition);
		return getGameViewAndUpdateModel(model);
	}
	
	private void createGameWithStatistics(Level level) {
    	game = new Game(level);
    	if (playerName == null)
    		playerName = "UNKNWON";
    	stats.addGame(playerName, game);
    }

    private String getGameViewAndUpdateModel(Model model) {
    	model.addAttribute("rows", game.getField().getFieldRows());
        model.addAttribute("status", game.getStatus());
        model.addAttribute("player", playerName);
        return VIEW_MINESWEEPER;
    }

}

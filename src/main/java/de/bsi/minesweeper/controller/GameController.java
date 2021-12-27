package de.bsi.minesweeper.controller;

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
import lombok.extern.slf4j.Slf4j;

@Controller
@SessionScope
@RequestMapping("/")
@Slf4j
public class GameController {
	
	private static final String VIEW_MINESWEEPER = "minesweeper";
	private static final String VIEW_LOGIN = "login";

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
    	game = new Game(Level.EASY);
    	return getGameViewAndUpdateModel(model);
    }
    
    /**
     * Opens one cell in minefield at given position to play a round.
     * If position is null, the Minesweeper game is restarted with a new minefield.
     */
    @PostMapping()
    public String openCellOrRestartGame(Model model, 
    		@RequestParam(required = false) String position) {
    	if (position != null)
    		game.playRound(Position.parse(position));
    	else
    		game = new Game(Level.EASY);		
        return getGameViewAndUpdateModel(model);
    }
    
    /**
     * Opens one cell in minefield at given position to play a round.
     * If position is null, the Minesweeper game is restarted with a new minefield.
     */
    @PostMapping("play")
    public String openCellToPlayOneRound(Model model, @RequestParam String position) {
    	log.info("Button of cell {} pressed.", position);
        game.playRound(Position.parse(position));        		
        return getGameViewAndUpdateModel(model);
    }
    
    @PostMapping("restart")
    public String restartGame(Model model, @RequestParam String level) {
    	log.info("Game restart in level {}.", level);
    	game = new Game(Level.valueOf(level));
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

    private String getGameViewAndUpdateModel(Model model) {
    	model.addAttribute("rows", game.getField().getFieldRows());
        model.addAttribute("status", game.getStatus());
        model.addAttribute("player", playerName);
        return VIEW_MINESWEEPER;
    }

}

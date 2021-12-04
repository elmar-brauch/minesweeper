package de.bsi.minesweeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.bsi.minesweeper.model.Game;
import de.bsi.minesweeper.model.Level;
import de.bsi.minesweeper.model.Position;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/")
@Slf4j
public class GameController {

    private Game game = new Game(Level.EASY);

    @GetMapping
    public String startPage(Model model) {
    	return getGameViewAndUpdateModel(model);
    }
    
    @PostMapping("play")
    public String openCellToPlayOneRound(Model model, @RequestParam String position) {
    	log.info("Button of cell {} pressed.", position);
        game.playRound(Position.parse(position));        		
        return getGameViewAndUpdateModel(model);
    }
    
    @PostMapping("restart")
    public String restartGame(Model model, @RequestParam String level) {
    	game = new Game(Level.valueOf(level));
    	return getGameViewAndUpdateModel(model);
    }

    private static final String VIEW_MINESWEEPER = "minesweeper";
    
    private String getGameViewAndUpdateModel(Model model) {
    	model.addAttribute("rows", game.getField().getFieldRows());
        model.addAttribute("status", game.getStatus());
        return VIEW_MINESWEEPER;
    }

}

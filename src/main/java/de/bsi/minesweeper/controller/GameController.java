package de.bsi.minesweeper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.bsi.minesweeper.model.Game;
import de.bsi.minesweeper.model.Level;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/")
@Slf4j
public class GameController {

    private static final String VIEW_MINESWEEPER = "minesweeper";
    
	private Game game = new Game(Level.EASY);

    @GetMapping
    public String startPage(Model model) {
    	updateModel(model);
        return VIEW_MINESWEEPER;
    }
    
    @PostMapping("play")
    public String openCellToPlayOneRound(Model model, @RequestParam String position) {
    	log.info("Button of cell {} pressed.", position);
        var rowAndCol = position.split(":");
        game.playRound(Integer.parseInt(rowAndCol[0]), Integer.parseInt(rowAndCol[1]));        		
    	updateModel(model);
    	return VIEW_MINESWEEPER;
    }
    
    @PostMapping("restart")
    public String restartGame(Model model, @RequestParam String level) {
    	game = new Game(Level.valueOf(level));
    	updateModel(model);
    	return VIEW_MINESWEEPER;
    }
    
    private void updateModel(Model model) {
    	model.addAttribute("rows", game.getField().getFieldRows());
        model.addAttribute("status", game.getStatus());
    }

}

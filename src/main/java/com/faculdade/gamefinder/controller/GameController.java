package com.faculdade.gamefinder.controller;

import com.faculdade.gamefinder.model.GameResponse;
import com.faculdade.gamefinder.service.RawgService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {

    private final RawgService rawgService;

    public GameController(RawgService rawgService) {
        this.rawgService = rawgService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        // Exibição dos dados consumidos
        GameResponse.Game game = rawgService.buscarJogo(query);

        if (game != null) {
            model.addAttribute("game", game);
        } else {
            // Tratamento de erro visual
            model.addAttribute("error", "Jogo não encontrado ou erro de comunicação.");
        }
        return "index";
    }
}
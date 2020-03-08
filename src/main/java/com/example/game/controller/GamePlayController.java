package com.example.game.controller;

import com.example.game.model.Game;
import com.example.game.model.Player;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/play")
@Service
public class GamePlayController {
    public String play(Authentication authentication){
        return authentication.getName();
    }
    public void submitAnswer(Player player, String answer){
        Game currentGame=new Game();
        currentGame.submitAnswer(player,answer);
    }
}

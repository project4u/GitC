package com.example.game.controller;

import com.example.game.exception.InvalidGameActionException;
import com.example.game.model.Game;
import com.example.game.model.Player;
import com.example.game.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/play")
@Service
public class GamePlayController {
    @Autowired
    private PlayerRepository playerRepository;
    @GetMapping("/")
    public String play(Authentication authentication){
        return authentication.getName();
    }
    @GetMapping("/submit-answer/{answer}")
    public void submitAnswer(Authentication authentication,@PathVariable(name = "answer") String answer) throws InvalidGameActionException {
        Player player=playerRepository.findByEmail(authentication.getName()).orElseThrow();
        player.getCurrentGame().submitAnswer(player,answer);
    }
}

package com.example.game.controller;

import com.example.game.exception.InvalidGameActionException;
import com.example.game.model.GameMode;
import com.example.game.model.Player;
import com.example.game.repositories.GameModeRepository;
import com.example.game.repositories.PlayerRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/play")
@RestController
public class GamePlayAPI {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameModeRepository gameModeRepository;

    @GetMapping("/")
    public JSONObject play(Authentication authentication){
        Player player=getCurrentPlayer(authentication);
        JSONObject response=new JSONObject();
        response.put("playerAlias",player.getAlias());
        JSONArray gameModes=new JSONArray();
        for(GameMode mode:gameModeRepository.findAll())
        {
            JSONObject gameMode=new JSONObject();
            gameMode.put("title",mode.getName());
            gameMode.put("picture",mode.getPicture());
            gameMode.put("description",mode.getDescription());
            gameModes.add(gameMode);
        }
        response.put("gameModes",gameModes);
        System.out.println(gameModes);
        return response;
    }

   /* @GetMapping("/submit-answer/{answer}")
    public void submitAnswer(Authentication authentication,@PathVariable(name = "answer") String answer) throws InvalidGameActionException {
        Player player=getCurrentPlayer(authentication);
        player.getCurrentGame().submitAnswer(player,answer);
    }
    */
    private Player getCurrentPlayer(Authentication authentication) {
        return playerRepository.findByEmail(authentication.getName()).orElseThrow();
    }

}

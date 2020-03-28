package com.example.game.controller;

import com.example.game.exception.InvalidGameActionException;
import com.example.game.model.Game;
import com.example.game.model.GameMode;
import com.example.game.model.GameStatus;
import com.example.game.model.Player;
import com.example.game.repositories.GameModeRepository;
import com.example.game.repositories.GameRepository;
import com.example.game.repositories.PlayerRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/play")
@RestController
public class GamePlayAPI {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameModeRepository gameModeRepository;
    @Autowired
    private GameRepository gameRepository;

    private  JSONObject getData(Player player){
        Game currentGame = player.getCurrentGame();
        JSONObject response=new JSONObject();
        response.put("playerAlias",player.getAlias());
        response.put("currentGame",currentGame==null?null:currentGame.getId());
        if(currentGame==null){
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
        }
        else{
            response.put("gameModes",currentGame.getGameState());
            response.put("gameState",currentGame.getGameStatus());
        }
        return response;
    }

    @GetMapping("/")
    public JSONObject play(Authentication authentication){
        Player player=getCurrentPlayer(authentication);
        return getData(player);
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

    @GetMapping("/create-game")
    public JSONObject createGame(Authentication authentication, @RequestParam(name = "mode") String gameMode,
                           @RequestParam(name = "rounds") Integer numRounds,
                           @RequestParam(name = "ellen") Boolean hasEllen){
        Player leader=getCurrentPlayer(authentication);
        GameMode mode=gameModeRepository.findByName(gameMode).orElseThrow();
        gameRepository.save(new Game.Builder().gameMode(mode).numRounds(numRounds).hasEllen(hasEllen).leader(leader).
                gameStatus(GameStatus.PLAYERS_JOINING).build());
        return getData(leader);
    }



    @GetMapping("luffy-Submit")
    public String luffySubmit() throws InvalidGameActionException {
        Player luffy=playerRepository.findByEmail("ds1234@gmail.com").orElseThrow();
        Game game=luffy.getCurrentGame();
        game.submitAnswer(luffy,"Answer");
        gameRepository.save(game);
        return "done";
    }

    @GetMapping("robin-Submit")
    public String robinSubmit() throws InvalidGameActionException {
        Player robin=playerRepository.findByEmail("jn1234@gmail.com").orElseThrow();
        Game game=robin.getCurrentGame();
        game.submitAnswer(robin,"Answer");
        gameRepository.save(game);
        return "done";
    }

}

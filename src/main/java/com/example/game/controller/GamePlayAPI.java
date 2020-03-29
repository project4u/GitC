package com.example.game.controller;

import com.example.game.Utils;
import com.example.game.exception.InvalidGameActionException;
import com.example.game.exception.PsychException;
import com.example.game.model.*;
import com.example.game.repositories.GameModeRepository;
import com.example.game.repositories.GameRepository;
import com.example.game.repositories.PlayerAnswerRepository;
import com.example.game.repositories.PlayerRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
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
    @Autowired
    private PlayerAnswerRepository playerAnswerRepository;
    private static JSONObject success;

    static {
        success = new JSONObject();
        success.put("status", "success");
    }

    @ExceptionHandler(PsychException.class)
    public JSONObject handleCustomException(Exception exception) {
        JSONObject error = new JSONObject();
        error.put("status", "error");
        error.put("errorText", exception.getMessage());
        return error;
    }

    private Player getCurrentPlayer(Authentication authentication) {
        return playerRepository.findByEmail(authentication.getName()).orElseThrow();
    }

    @GetMapping("/game-modes")
    public JSONArray gameModes() {
        JSONArray gameModes = new JSONArray();
        for (GameMode mode : gameModeRepository.findAll()) {
            JSONObject gameMode = new JSONObject();
            gameMode.put("title", mode.getName());
            gameMode.put("image", mode.getPicture());
            gameMode.put("description", mode.getDescription());
            gameModes.add(gameMode);
        }
        return gameModes;
    }

    @GetMapping("/player-data")
    public JSONObject playerData(Authentication authentication) {
        return getData(getCurrentPlayer(authentication));
    }

    private  JSONObject getData(Player player){
        JSONObject data = new JSONObject();
        data.put("alias", player.getAlias());
        data.put("picURL", player.getPicURL());
        data.put("psychFaceURL", player.getPsychFaceURL());
        data.put("email", player.getEmail());
        data.put("currentGameId", player.getCurrentGame() == null ? null : player.getCurrentGame().getId());
        JSONObject stats = new JSONObject();
        stats.put("correctAnswerCount", player.getStats().getCorrectAnswerCount());
        stats.put("gotPsychedCount", player.getStats().getGamePsychedCount());
        stats.put("psychedOthersCount", player.getStats().getPsychedOthersCount());
        data.put("stats", stats);
        return data;
    }

    @GetMapping("/game-state")
    public JSONObject gameState(Authentication authentication) throws Exception {
        return gameState(getCurrentPlayer(authentication).getCurrentGame());
    }

    public JSONObject gameState(Game game) {
        JSONObject data = new JSONObject();
        if (game == null) return data;
        data.put("id", game.getId());
        data.put("secretCode", game.getSecretCode());
        data.put("numRounds", game.getNumRounds());
        data.put("gameMode", game.getGameMode());
        data.put("hasEllen", game.getHasEllen());
        data.put("status", game.getGameStatus());
        try {
            data.put("round", game.getRoundData());
        } catch (InvalidGameActionException ignored) {
        }
        return data;
    }

    @GetMapping("/leaderboard")
    public JSONArray leaderboard() {
        JSONArray data = new JSONArray();
        for (Player player : playerRepository.findAll()) {
            JSONObject stats = new JSONObject();
            stats.put("alias", player.getAlias());
            stats.put("picURL", player.getPicURL());
            stats.put("correctAnswerCount", player.getStats().getCorrectAnswerCount());
            stats.put("gotPsychedCount", player.getStats().getGamePsychedCount());
            stats.put("psychedOthersCount", player.getStats().getPsychedOthersCount());
            data.add(stats);
        }
        return data;
    }

    @GetMapping("/update-profile")
    public JSONObject createGame(Authentication authentication,
                                 @RequestParam(name = "alias") String alias,
                                 @RequestParam(name = "email") String email,
                                 @RequestParam(name = "psychFaceURL") String psychFaceURL,
                                 @RequestParam(name = "picURL") String picURL) {
        Player player = getCurrentPlayer(authentication);
        player.setAlias(alias);
        player.setEmail(email);
        player.setPsychFaceURL(psychFaceURL);
        player.setPicURL(picURL);
        playerRepository.save(player);
        return success;
    }

    @GetMapping("/create-game")
    public JSONObject createGame(Authentication authentication, @RequestParam(name = "gameMode") String gameMode,
                                 @RequestParam(name = "numRounds") Integer numRounds,
                                 @RequestParam(name = "hasEllen") Boolean hasEllen){
        Player leader=getCurrentPlayer(authentication);
        System.out.println("inside fetch create-game :"+gameMode+" "+numRounds+" "+hasEllen);
        GameMode mode=gameModeRepository.findByName(gameMode).orElseThrow();
        gameRepository.save(new Game.Builder().gameMode(mode).numRounds(numRounds).hasEllen(hasEllen).leader(leader).
                gameStatus(GameStatus.PLAYERS_JOINING).build());
        return getData(leader);
    }

    @GetMapping("/join-game")
    public JSONObject joinGame(Authentication authentication,
                               @RequestParam(name="code") String secretCode) throws InvalidGameActionException {
        Player player=getCurrentPlayer(authentication);
        Game game=gameRepository.findById(Utils.getGameIdFromSecretCode(secretCode)).orElseThrow();
        game.addPlayer(player);
        return getData(player);
    }

    @GetMapping("/leave-game")
    public JSONObject leaveGame(Authentication authentication) throws InvalidGameActionException {
        Player player=getCurrentPlayer(authentication);
        player.getCurrentGame().removePlayer(player);
        return getData(player);
    }

    @GetMapping("/start-game")
    public JSONObject startGame(Authentication authentication) throws InvalidGameActionException {
        Player leader=getCurrentPlayer(authentication);
        leader.getCurrentGame().startGame(leader);
        return getData(leader);
    }

    @GetMapping("/end-game")
    public JSONObject endGame(Authentication authentication) throws InvalidGameActionException {
        Player leader=getCurrentPlayer(authentication);
        leader.getCurrentGame().endGame(leader);
        return getData(leader);
    }

    @GetMapping("/submit-answer")
    public JSONObject submitAnswer(Authentication authentication,
                                   @RequestParam(name = "answer") String answer) throws InvalidGameActionException {
        Player player=getCurrentPlayer(authentication);
        Game game=player.getCurrentGame();
        game.submitAnswer(player,answer);
        return getData(player);
    }

    @GetMapping("/select-answer")
    public JSONObject selectAnswer(Authentication authentication, @RequestParam(name = "playerAnswerId") Long playerAnswerId) throws InvalidGameActionException {
        Player player = getCurrentPlayer(authentication);
        Game game = player.getCurrentGame();
        PlayerAnswer playerAnswer = playerAnswerRepository.findById(playerAnswerId).orElseThrow();
        game.selectAnswer(player, playerAnswer);
        return success;
    }

    @GetMapping("/player-ready")
    public JSONObject playerReady(Authentication authentication) throws InvalidGameActionException {
        Player player = getCurrentPlayer(authentication);
        Game game = player.getCurrentGame();
        game.playerIsReady(player);
        return success;
    }

    @GetMapping("/player-not-ready")
    public JSONObject playerNotReady(Authentication authentication) throws InvalidGameActionException {
        Player player = getCurrentPlayer(authentication);
        Game game = player.getCurrentGame();
        game.playerIsNotReady(player);
        return success;
    }

    /*@GetMapping("/")
    public JSONObject play(Authentication authentication){
        Player player=getCurrentPlayer(authentication);
        return getData(player);
    }*/

   /* @GetMapping("/submit-answer/{answer}")
    public void submitAnswer(Authentication authentication,@PathVariable(name = "answer") String answer) throws InvalidGameActionException {
        Player player=getCurrentPlayer(authentication);
        player.getCurrentGame().submitAnswer(player,answer);
    }
    */




    /*@GetMapping("luffy-Submit")
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
*/
}

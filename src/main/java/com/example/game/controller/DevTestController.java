package com.example.game.controller;

import com.example.game.Constants;
import com.example.game.Pair;
import com.example.game.Utils;
import com.example.game.exception.InvalidGameActionException;
import com.example.game.model.*;
import com.example.game.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/")
public class DevTestController {

    @GetMapping("/dev-test")
    public String sayHello(){
        return "Hello World!!!sdasfd";
    }

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private GameModeRepository gameModeRepository;

    @GetMapping("/dev-test/players")
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    @GetMapping("/dev-test/players/{id}")
    public Player getPlayerById(@PathVariable(name="id") Long id){
        return playerRepository.findById(id).orElseThrow();
    }

    @GetMapping("/dev-test/questions")
    public List<Question> getAllQuestion(){ return questionRepository.findAll();}

    @GetMapping("/dev-test/question/{id}")
    public Question getQuestionById(@PathVariable(name="id") Long id){return questionRepository.findById(id).orElseThrow();}

    @GetMapping("/dev-test/games")
    public List<Game> getAllGames(){return gameRepository.findAll();}

    @GetMapping("/dev-test/games/{id}")
    public Game getGameById(@PathVariable(name="id") Long id){ return gameRepository.findById(id).orElseThrow();}

    @GetMapping("/dev-test/users")
    public List<User> getAllUsers(){return userRepository.findAll();}

    @GetMapping("/dev-test/users/{id}")
    public User getUserById(@PathVariable(name="id") Long id){ return userRepository.findById(id).orElseThrow();}

    @GetMapping("/dev-test/rounds")
    public List<Round> getAllRounds(){return roundRepository.findAll();}

    @GetMapping("/dev-test/rounds/{id}")
    public Round getRoundById(@PathVariable(name="id") Long id){ return roundRepository.findById(id).orElseThrow();}

    @GetMapping("/dev-test/populate")
    public String populateDB(){
        for (Player player : playerRepository.findAll()) {
            player.getGames().clear();
            player.setCurrentGame(null);
            playerRepository.save(player);
        }
        gameRepository.deleteAll();
        playerRepository.deleteAll();
        questionRepository.deleteAll();
        gameModeRepository.deleteAll();

        Player luffy = new Player.Builder()
                .alias("Monkey D. Luffy")
                .picURL("https://i.pinimg.com/originals/f0/ba/81/f0ba812b436a81ccbd00ddc6c97a8a8d.png")
                .psychFaceURL("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSYxu5_eX0q1LCdfuioGV5lZ4xUz0hAgUI4BiIOx2-Z0Lra8k7G")
                .email("luffy@gmail.com")
                .saltedHashedPassword("abcd1234")
                .build();
        playerRepository.save(luffy);
        Player robin = new Player.Builder()
                .alias("Nico Robin")
                .picURL("https://static.zerochan.net/Nico.Robin.full.2503480.jpg")
                .psychFaceURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTSp50zfx3maCYAU5FfNdLEW9546d6xhTA53u78_aFVXgo3r_f27w&s")
                .email("robin@gmail.com")
                .saltedHashedPassword("efgh1234")
                .build();
        playerRepository.save(robin);
        Player nami = new Player.Builder()
                .alias("Nami")
                .picURL("https://static1.cbrimages.com/wordpress/wp-content/uploads/2019/12/Featured-Image-Nami-Cropped.jpg")
                .psychFaceURL("https://i.pinimg.com/474x/4a/02/2f/4a022face87c1bffb390465928352456.jpg")
                .email("nami@psych.com")
                .saltedHashedPassword("mikan")
                .build();
        playerRepository.save(nami);
        Player franky = new Player.Builder()
                .alias("Cutty Flam")
                .picURL("https://vignette.wikia.nocookie.net/villains/images/2/2a/Franky1.png/revision/latest/scale-to-width-down/340?cb=20130604001757")
                .psychFaceURL("https://66.media.tumblr.com/dd9da7116839d4e3a52428c26eeeec8c/tumblr_ml8rgzurh01reoa3zo1_500.gif")
                .email("franky@psych.com")
                .saltedHashedPassword("pluton")
                .build();
        playerRepository.save(franky);
        Player zoro = new Player.Builder()
                .alias("Zoro")
                .email("zoro@psych.com")
                .saltedHashedPassword("santoryu")
                .build();
        playerRepository.save(zoro);
        Player sanji = new Player.Builder()
                .alias("Vinsmoke Sanji")
                .email("sanji@psych.com")
                .saltedHashedPassword("allblue")
                .build();
        playerRepository.save(sanji);
        Player vivi = new Player.Builder()
                .alias("Nefratari Vivi")
                .email("vivi@psych.com")
                .saltedHashedPassword("alabasta")
                .build();
        playerRepository.save(vivi);
        Player brook = new Player.Builder()
                .alias("Brook")
                .email("brook@psych.com")
                .saltedHashedPassword("laboon")
                .build();
        playerRepository.save(brook);
        Player usopp = new Player.Builder()
                .alias("Usopp")
                .email("usopp@psych.com")
                .saltedHashedPassword("kaya")
                .build();
        playerRepository.save(usopp);
        Player chopper = new Player.Builder()
                .alias("Tony Tony Chopper")
                .email("chopper@psych.com")
                .saltedHashedPassword("sakura")
                .build();
        playerRepository.save(chopper);
        Player merry = new Player.Builder()
                .alias("Going Merry")
                .email("merry@psych.com")
                .saltedHashedPassword("bokennoumi")
                .build();
        playerRepository.save(merry);
        Player sunny = new Player.Builder()
                .alias("Thousand Sunny")
                .email("sunny@psych.com")
                .saltedHashedPassword("gaonhou")
                .build();
        playerRepository.save(sunny);
        Player jinbe = new Player.Builder()
                .alias("Jinbe")
                .email("jinbe@psych.com")
                .saltedHashedPassword("gyojin")
                .build();
        playerRepository.save(jinbe);
        Player carrot = new Player.Builder()
                .alias("Carrot")
                .email("carrot@psych.com")
                .saltedHashedPassword("pedro")
                .build();
        playerRepository.save(carrot);

        GameMode isThisAFact = new GameMode("Is This A Fact", "/images/is_that_a_fact.png", "These remarkable facts may surprise you, but so will your friends' fake answers! Come up with your own answers, and win a point for every player you psych into thinking your false fact is right. Then try to pick the real fact!");
        gameModeRepository.save(isThisAFact);
        gameModeRepository.save(new GameMode("Proverbs", "/images/proverb.jpeg", "A half truth is a whole lie. Take these proverbs and write your own endings. Win a point for every player you psych into thinking your ending is right, then try to pick the real one!"));
        gameModeRepository.save(new GameMode("Animals", "/images/animal.png", "You won't believe these animal facts, because most of them will be made up by you and your friends! Write a fake answer to these real animal facts."));
        gameModeRepository.save(new GameMode("And The Truth Comes Out", "/images/truth.jpg", "\"If David were arrested tomorrow, it would probably be for this.\" In this mode, you and your friends become the game! Write the best answer about your friends, then choose your favorite answers."));
        gameModeRepository.save(new GameMode("Movie Buff", "/images/movie_buff.jpeg", "These movie titles all belong to very real movies. Make up your own plot and win a point for every player you psych into thinking your plot is the correct one. Then try to pick the real movie plot!"));
        gameModeRepository.save(new GameMode("Word Up", "/images/word_up.jpeg", "This mode is packed with real definitions to unsual words. Make up a word for each definition and win a point for every player you psych into thinking that your word is correct. Then try to pick the real one!"));
        gameModeRepository.save(new GameMode("Un-Scramble", "/images/unscramble.jpeg", "unscramble description"));

        List<Question> questions = new ArrayList<>();
        for (Map.Entry<String, String> fileMode : Constants.QA_FILES.entrySet()) {
            GameMode gameMode = gameModeRepository.findByName(fileMode.getValue()).orElseThrow();
            for (Pair<String, String> questionAnswer : Utils.readQAFile(fileMode.getKey())) {
                questions.add(new Question.Builder().question(questionAnswer.getFirst()).
                        correctAnswer(questionAnswer.getSecond()).
                        gameMode(gameMode).build());
            }
        }
        questionRepository.saveAll(questions);
        return "populated";
    }
}

package com.example.game.controller;

import com.example.game.model.*;
import com.example.game.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/dev-test")
public class DevTestController {

    @GetMapping("/dev-test")
    public String sayHello(){
        return "Hello World";
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
        gameRepository.deleteAll();
        playerRepository.deleteAll();
        questionRepository.deleteAll();

        Player player1 =new Player.Builder()
                .alias("Dharmendra S Singh")
                .saltedHashedPassword("abcd1234")
                .email("ds1234@gmail.com")
                .build();
        Player player2 =new Player.Builder()
                .alias("John & John")
                .saltedHashedPassword("john1234")
                .email("jn1234@gmail.com")
                .build();
        Question q1=new Question.Builder()
                .question("What is HashMap")
                .correctAnswer("Map")
                .gameMode(GameMode.IS_THIS_A_FACT)
                .build();
        Game g1=new Game.Builder()
                .numRounds(10)
                .gameMode(GameMode.IS_THIS_A_FACT)
                .gameStatus(GameStatus.PLAYERS_JOINING)
                .hasEllen(false)
                .leader(player1)
                .build();
        g1.getPlayers().add(player1);
        playerRepository.save(player1);
        playerRepository.save(player2);
        questionRepository.save(q1);
        gameRepository.save(g1);

        return "populated";
    }
}

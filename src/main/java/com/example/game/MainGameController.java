package com.example.game;

import com.example.game.model.Player;
import com.example.game.repositories.GameRepository;
import com.example.game.repositories.PlayerRepository;
import com.example.game.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/")
public class MainGameController {
    @GetMapping("/")
    public String sayHello(){
        return "Hello World";
    }

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/players")
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    @GetMapping("/players/{id}")
    public Player getPlayerById(@PathVariable(name="id") Long id){
        return playerRepository.findById(id).orElseThrow();
    }

    @GetMapping("/populate")
    public String populateDB(){
        playerRepository.deleteAll();
        questionRepository.deleteAll();
        gameRepository.deleteAll();
        Player player1 =new Player.Builder()
                .alias("Dharmendra S Singh")
                .saltedHashedPassword("abcd1234")
                .email("ds1234@gmail.com")
                .build();
        playerRepository.save(player1);
        Player player2 =new Player.Builder()
                .alias("John & John")
                .saltedHashedPassword("john1234")
                .email("jn1234@gmail.com")
                .build();
        playerRepository.save(player2);
        return "populated";
    }
}

package com.example.game.model;

import com.example.game.exception.InvalidGameActionException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "rounds")
public class Round extends Auditable{
    @ManyToOne
    @Getter @Setter
    @NotNull
    @JsonBackReference
    private Game game;

    @ManyToOne
    @NotNull
    @Getter @Setter
    @JsonIdentityReference
    private Question question;

    @ManyToMany(cascade = CascadeType.ALL)
    @Getter @Setter
    @JsonManagedReference
    private Map<Player,PlayerAnswer> playerAnswer = new HashMap<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @Getter @Setter
    @JsonManagedReference
    private Map<Player,PlayerAnswer> selectedAnswers = new HashMap<>();

    @ManyToOne
    @Getter @Setter
    private EllenAnswer ellenAnswer;

    @Getter @Setter
    @NotNull
    private int roundNumber;

    public Round(Game game, Question randomQuestion, int roundNumber) {
        super();
        this.game=game;
        this.question=randomQuestion;
        this.roundNumber=roundNumber;
    }

    public Round() {

    }

    public void submitAnswer(Player player, String answer) throws InvalidGameActionException {
        if(playerAnswer.containsKey(player))
            throw new InvalidGameActionException("player has already submitted answer for this round");
        for(PlayerAnswer existingAnswer : playerAnswer.values())
            if(answer.equals(existingAnswer.getAnswer()))
                throw new InvalidGameActionException("Duplicate Answer");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playerAnswer.put(player,new PlayerAnswer(this,player,answer));
    }

    public boolean allAnswersSubmitted(int numPlayers) {
        return playerAnswer.size()==numPlayers;
    }

    public void selectAnswer(Player player, PlayerAnswer selectedAnswer) throws InvalidGameActionException {
        if(selectedAnswers.containsKey(player))
            throw new InvalidGameActionException("player has already selected answer for this round");
        if(selectedAnswer.getPlayer().equals(player))
            throw new InvalidGameActionException("Can't Select your own answer");
        selectedAnswers.put(player,selectedAnswer);
    }

    public boolean allAnswersSelected(int numPlayers) {
        return selectedAnswers.size()==numPlayers;
    }
}

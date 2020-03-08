package com.example.game.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "playerAnswers")
public class PlayerAnswer extends Auditable{
    @NonNull
    @ManyToOne
    @Getter @Setter
    private Round round;

    @NotNull
    @ManyToOne
    @Getter @Setter
    private Player player;

    @NotBlank
    @Getter @Setter
    private String answer;

    public PlayerAnswer(Round round, Player player, String answer) {
        super();
        this.round=round;
        this.player=player;
        this.answer=answer;
    }
}

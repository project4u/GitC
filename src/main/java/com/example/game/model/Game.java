package com.example.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends Auditable{
    @ManyToMany
    @Getter @Setter
    @JsonIdentityReference
    private Set<Player> players=new HashSet<>();

    @NotNull
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private GameMode gameMode;

    @OneToMany(mappedBy = "game",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Round> rounds=new ArrayList<>();

    @Getter @Setter
    private int numRounds = 10;

    @Getter @Setter
    private Boolean hasEllen =false;

    @Getter @Setter
    @ManyToOne
    @NotNull
    @JsonIdentityReference
    private Player leader;

    @ManyToMany(cascade = CascadeType.ALL)
    @Getter @Setter
    @JsonIdentityReference
    private Map<Player, Stat> playerStats = new HashMap<>();

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @ManyToMany
    @Getter @Setter
    private Set<Player> readyPlayers = new HashSet<>();

    public Game(){}

    public Game(Builder builder) {
        super();
        setGameMode(builder.gameMode);
        setNumRounds(builder.numRounds);
        setHasEllen(builder.hasEllen);
        setGameStatus(builder.gameStatus);
        setLeader(builder.leader);
    }

    public static final class Builder{
        private @NotNull GameMode gameMode;
        private Integer numRounds = 10;
        private Boolean hasEllen =false;
        private GameStatus gameStatus;
        private @NotNull Player leader;

        public Builder gameMode(@NotNull GameMode val){
            gameMode=val;
            return this;
        }
        public Builder numRounds(Integer val){
            numRounds=val;
            return this;
        }
        public Builder hasEllen(Boolean val){
            hasEllen=val;
            return this;
        }
        public Builder gameStatus(GameStatus val){
            gameStatus=val;
            return this;
        }
        public Builder leader(Player val){
            leader=val;
            return this;
        }
        public Game build(){
            return new Game(this);
        }
    }
}

package com.example.game.model;

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
    private Set<Player> players;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GameMode gameMode;

    @OneToMany(mappedBy = "game",cascade = CascadeType.ALL)
    private List<Round> rounds=new ArrayList<>();

    @Getter @Setter
    private int numRounds = 10;

    @Getter @Setter
    private Boolean hasEllen =false;

    @Getter @Setter
    @ManyToOne
    @NotNull
    private Player leader;

    @ManyToMany(cascade = CascadeType.ALL)
    @Getter @Setter
    private Map<Player, Stat> playerStats = new HashMap<>();

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @ManyToMany
    @Getter @Setter
    private Set<Player> readyPlayers = new HashSet<>();
}

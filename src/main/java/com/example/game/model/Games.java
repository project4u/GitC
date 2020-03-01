package com.example.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToMany;
import java.util.Set;

public class Games extends Auditable{
    @ManyToMany
    @Getter @Setter
    private Set<Player> players;

    private GameMode gameMode;
}

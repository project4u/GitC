package com.example.game.model;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "stats")
public class Stat extends Auditable{
    @Getter @Setter
    private long gamePsychedCount=0l;
    @Getter @Setter
    private long psychedOthersCount=0l;
    @Getter @Setter
    private long correctAnswerCount=0l;
}

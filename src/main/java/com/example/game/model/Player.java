package com.example.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="players")
public class Player extends User{
    @NotBlank
    @Getter @Setter
    private String alias;

    @Getter @Setter
    private String pyschFaceURL;

    @Getter @Setter
    private String picURL;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter @Setter
    private Stats stats=new Stats();

    @Getter @Setter
    @ManyToMany(mappedBy = "players")
    private Set<Games> games=new HashSet<>();
}

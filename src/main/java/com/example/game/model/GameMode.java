package com.example.game.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="game_modes")
public class GameMode extends Auditable{
    public GameMode(@NotBlank String name, @URL String picture, String description) {
        this.name = name;
        this.picture = picture;
        this.description = description;
    }

    public GameMode(){
    }

    @Getter @Setter
    @NotBlank
    @Column(unique = true)
    private String name;

    @Getter @Setter
    @URL
    private String picture;

    @Getter @Setter
    private String description;
}

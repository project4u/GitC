package com.example.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name="players")
public class Player extends Auditable{
    @NotBlank
    @Getter @Setter
    private String alias;

    @Getter @Setter
    private String pyschFaceURL;

    @Getter @Setter
    private String picURL;
}

package com.example.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="players")
public class Player extends User{
    @NotBlank
    @Getter @Setter
    private String alias;

    @Getter @Setter
    private String psychFaceURL;

    @Getter @Setter
    private String picURL;

    @OneToOne(cascade = CascadeType.ALL)
    @Getter @Setter
    @JsonManagedReference
    private Stat stats=new Stat();

    @Getter @Setter
    @ManyToMany(mappedBy = "players")
    @JsonIdentityReference
    private Set<Game> games=new HashSet<>();

    @Getter @Setter
    @JsonIdentityReference
    @ManyToOne
    private Game currentGame=null;

    public Player(){}

    private Player (Builder builder){
        setEmail(builder.email);
        setSaltedHashedPassword(builder.saltedHashedPassword);
        setAlias(builder.alias);
        setPsychFaceURL(builder.psychFaceURL);
        setPicURL(builder.picURL);
        //setCurrentGame(builder.currentGame);
    }

    public static final class Builder{
        private @Email @NotBlank String email;
        private @NotBlank String saltedHashedPassword;
        private @NotBlank String alias;
        private String psychFaceURL;
        private String picURL;
        private Game currentGame;

        public Builder(){

        }
        public Builder email(@Email @NotBlank String val){
            email=val;
            return this;
        }
        public Builder saltedHashedPassword(@NotBlank String val){
            saltedHashedPassword=val;
            return this;
        }
        public Builder alias(@NotBlank String val){
            alias=val;
            return this;
        }
        public Builder psychFaceURL(String val){
            psychFaceURL=val;
            return this;
        }
        public Builder picURL(String val){
            picURL=val;
            return this;
        }
        /*public Builder currentGame(Game val){
            currentGame=val;
            return this;
        }*/
        public Player build(){
            return new Player(this);
        }
    }
}

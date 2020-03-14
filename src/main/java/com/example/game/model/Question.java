package com.example.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.NonFinal;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question extends Auditable{
    @NotNull @Getter @Setter
    private String question;
    @NotNull @Getter @Setter
    private String correctAnswer;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    @Getter @Setter
    @JsonIdentityReference
    Set<EllenAnswer> ellenAnswer =new HashSet<>();

    @NotNull @Getter @Setter
    @ManyToOne
    @JsonIdentityReference
    private GameMode gameMode;

    public Question(){}

    public Question(Builder builder) {
        super();
        setQuestion(builder.question);
        setCorrectAnswer(builder.correctAnswer);
        setGameMode(builder.gameMode);
    }

    public static final class Builder{
        private @NotNull  String question;
        private @NotNull String correctAnswer;
        private @NotNull  GameMode gameMode;
        public Builder(){}
        public Builder question(@NotNull String val){
            question=val;
            return this;
        }
        public Builder correctAnswer(@NotNull String val){
            correctAnswer=val;
            return this;
        }
        public Builder gameMode(@NotNull GameMode val){
            gameMode=val;
            return this;
        }
        public Question build(){
            return new Question(this);
        }
    }
}

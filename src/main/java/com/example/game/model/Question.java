package com.example.game.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.NonFinal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
public class Question extends Auditable{
    @NonNull @Getter @Setter
    private String question;
    @NonNull @Getter @Setter
    private String correctAnswer;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    @Getter @Setter
    Set<EllenAnswer> ellenAnswer =new HashSet<>();

    @NonNull @Getter @Setter
    private GameMode gameMode;
}

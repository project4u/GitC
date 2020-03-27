package com.example.game.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ellenAnswers")
public class EllenAnswer extends Auditable{
    @ManyToOne
    @NotNull
    @Getter @Setter
    private Question question;

    @Getter @Setter
    private long votes=0l;

    @NotBlank
    @Getter @Setter
    private String answer;

    public EllenAnswer(){}

}

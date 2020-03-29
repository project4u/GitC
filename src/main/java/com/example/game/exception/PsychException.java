package com.example.game.exception;

import lombok.Getter;
import lombok.Setter;

public class PsychException extends Throwable {
    @Getter @Setter
    private String message;
    public PsychException(String message){
        super();
        this.message=message;
    }
}

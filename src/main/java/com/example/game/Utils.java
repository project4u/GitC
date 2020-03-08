package com.example.game;

import com.example.game.config.SpringConfiguration;
import com.example.game.model.EllenAnswer;
import com.example.game.model.GameMode;
import com.example.game.model.Question;
import com.example.game.repositories.EllenAnswerRepository;
import com.example.game.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class Utils {
    private static QuestionRepository questionRepository;
    private static EllenAnswerRepository ellenAnswerRepository;
    static {
        questionRepository= (QuestionRepository)SpringConfiguration.contextProvider().getApplicationContext().getBean("questionRepository");
        ellenAnswerRepository=(EllenAnswerRepository)SpringConfiguration.contextProvider().getApplicationContext().getBean("ellenAnswerRepository");
    }
    //autowired work if class is service,controller,restcontroller
    public static Question getRandomQuestion(GameMode gameMode) {
        return questionRepository.getRandomQuestion(gameMode);
    }

    public static EllenAnswer getRandomEllenAnswer(Question question) {
        return ellenAnswerRepository.getRandomAnswer(question);
    }
}

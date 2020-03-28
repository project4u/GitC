package com.example.game;

import com.example.game.config.ApplicationContextProvider;
import com.example.game.config.SpringConfiguration;
import com.example.game.model.EllenAnswer;
import com.example.game.model.GameMode;
import com.example.game.model.Question;
import com.example.game.repositories.EllenAnswerRepository;
import com.example.game.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {
    private static QuestionRepository questionRepository;
    private static EllenAnswerRepository ellenAnswerRepository;
    private static List<String> wordsList;
    private static HashMap<String ,Integer> wordIndices;
    static {
        /*questionRepository= (QuestionRepository)SpringConfiguration.contextProvider().getApplicationContext().getBean("questionRepository");
        ellenAnswerRepository=(EllenAnswerRepository)SpringConfiguration.contextProvider().getApplicationContext().getBean("ellenAnswerRepository");*/
        questionRepository= (QuestionRepository) ApplicationContextProvider.getApplicationContext().getBean("questionRepository");
        ellenAnswerRepository=(EllenAnswerRepository)ApplicationContextProvider.getApplicationContext().getBean("ellenAnswerRepository");
        try {
            BufferedReader br=new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:data/words.txt")));
            String word;
            wordsList=new ArrayList<>();
            wordIndices=new HashMap<>();
            int index=0;
            do{
                word=br.readLine();
                if(word==null)
                    break;
                word=word.strip();
                wordsList.add(word);
                wordIndices.put(word,index);
                index+=1;
            }while (word!=null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //autowired work if class is service,controller,restcontroller
    public static Question getRandomQuestion(GameMode gameMode) {
        return questionRepository.getRandomQuestion(gameMode.getId());
    }

    public static EllenAnswer getRandomEllenAnswer(Question question) {
        return ellenAnswerRepository.getRandomAnswer(question.getId());
    }

    public String getSecretCodeFromGameId(Long id){
        String code="";
        int base=wordsList.size();
        while(id>0){
            code+=wordsList.get((int) (id%base));
            id=id/base;
        }
        return code.substring(0,code.length()-1);
    }

    public Long getGameIdFromSecretCode(String code){
        String words[]=code.split(" ");
        long gameId=0l;
        int base=wordsList.size();
        for (String word:words){
            gameId=gameId*base+wordIndices.get(word);
        }
        return gameId;
    }

    public static List<Pair<String ,String>> readQAFile(String fileName){
        //returns QA pair
        List<Pair<String ,String>> questions=new ArrayList<>();
        int count=0;
        try{
            BufferedReader br=new BufferedReader(new FileReader(ResourceUtils.getFile(fileName)));
            String questiontext,correctAnswer;
            do{
                questiontext=br.readLine();
                correctAnswer=br.readLine();
                if(questiontext==null || correctAnswer==null)
                    break;
                questiontext=questiontext.strip();
                correctAnswer=correctAnswer.strip();
                questions.add(new Pair<>(questiontext,correctAnswer));
                count++;
            }while (questiontext!=null && count<Constants.MAX_QUESTIONS_TO_READ);
        }
        catch (IOException ignored){

        }
        return questions;
    }
}

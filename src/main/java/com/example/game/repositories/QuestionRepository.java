package com.example.game.repositories;

import com.example.game.model.GameMode;
import com.example.game.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    //JPA ->dba agnostic
    //native -> postgres
    @Query(value = "SELECT * FROM questions WHERE game_mode_id=:gameModeId ORDER BY RANDOM() LIMIT 1",nativeQuery = true)
    Question getRandomQuestion(Long gameModeId);
}

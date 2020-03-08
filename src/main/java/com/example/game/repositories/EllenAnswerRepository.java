package com.example.game.repositories;

import com.example.game.model.EllenAnswer;
import com.example.game.model.GameMode;
import com.example.game.model.GameStatus;
import com.example.game.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EllenAnswerRepository extends JpaRepository<EllenAnswer,Long> {
    @Query(value = "",nativeQuery = true)
    EllenAnswer getRandomAnswer(Question question);
}

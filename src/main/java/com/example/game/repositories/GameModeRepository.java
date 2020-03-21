package com.example.game.repositories;

import com.example.game.model.GameMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameModeRepository extends JpaRepository<GameMode,Long> {
}
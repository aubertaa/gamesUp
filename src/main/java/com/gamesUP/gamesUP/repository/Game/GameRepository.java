package com.gamesUP.gamesUP.repository.Game;

import com.gamesUP.gamesUP.model.Game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g WHERE g.nom = :name")
    List<Game> findByName(String name);

    List<Game> findByGenre(String genre);

    List<Game> findByPublisher(String publisher);

    List<Game> findByCategory(String category);

}

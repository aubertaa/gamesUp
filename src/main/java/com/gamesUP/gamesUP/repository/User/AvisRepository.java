package com.gamesUP.gamesUP.repository.User;

import com.gamesUP.gamesUP.model.User.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Long> {

    @Query("SELECT a.note FROM Avis a WHERE a.user.id = :user_id AND a.game.id = :game_id")
    Float findByGameAndUserId(Long game_id, Long user_id);
}

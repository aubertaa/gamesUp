package com.gamesUP.gamesUP.repository.User;

import com.gamesUP.gamesUP.model.User.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Long> {
}

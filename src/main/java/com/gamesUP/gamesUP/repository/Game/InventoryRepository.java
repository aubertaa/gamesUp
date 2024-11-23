package com.gamesUP.gamesUP.repository.Game;

import com.gamesUP.gamesUP.model.Game.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}

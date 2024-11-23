package com.gamesUP.gamesUP.repository.Purchase;

import com.gamesUP.gamesUP.model.Purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}

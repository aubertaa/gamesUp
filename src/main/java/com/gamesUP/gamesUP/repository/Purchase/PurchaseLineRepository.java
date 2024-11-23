package com.gamesUP.gamesUP.repository.Purchase;

import com.gamesUP.gamesUP.model.Purchase.PurchaseLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseLineRepository extends JpaRepository<PurchaseLine, Long> {
}

package com.gamesUP.gamesUP.repository.Purchase;

import com.gamesUP.gamesUP.model.Purchase.PurchaseLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseLineRepository extends JpaRepository<PurchaseLine, Long> {

    @Query("SELECT pl FROM PurchaseLine pl WHERE pl.purchase.id = ?1")
    List<PurchaseLine> findByPurchaseId(Long id);
}

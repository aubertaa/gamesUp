package com.gamesUP.gamesUP.repository.Purchase;

import com.gamesUP.gamesUP.model.Purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT p FROM Purchase p WHERE p.user.id = ?1")
    List<Purchase> findByUserId(Long userId);

}

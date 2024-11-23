package com.gamesUP.gamesUP.repository.User;

import com.gamesUP.gamesUP.model.User.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}

package com.gamesUP.gamesUP.controller.User;

import com.gamesUP.gamesUP.DTO.User.WishlistDTO;
import com.gamesUP.gamesUP.DTO.User.WishlistResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.User.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {
    @Autowired
    private WishlistService wishListService;

    @PreAuthorize("hasRole('administrateur')")
    @GetMapping
    public List<WishlistResponseDTO> getAllWishlists() {
        return wishListService.getAllWishlists();
    }

    @PreAuthorize("hasAnyRole('administrateur', 'client')")
    @GetMapping("/{id}")
    public WishlistResponseDTO getWishlistById(@PathVariable Long id) {
        return wishListService.getWishlistById(id);
    }

    @PreAuthorize("hasAnyRole('administrateur', 'client')")
    @PostMapping
    public void addWishlist(@RequestBody WishlistDTO wishListDTO) {
        try {
            wishListService.addWishlist(wishListDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("wishlist already exists for this user");
        }
    }

    @PreAuthorize("hasAnyRole('administrateur', 'client')")
    @PutMapping("/{id}")
    public void updateWishlist(@PathVariable Long id, @RequestBody WishlistDTO wishListDTO) {
        wishListService.updateWishlist(id, wishListDTO);
    }

    @PreAuthorize("hasAnyRole('administrateur', 'client')")
    @DeleteMapping("/{id}")
    public void deleteWishlist(@PathVariable Long id) {
        wishListService.deleteWishlist(id);
    }
}

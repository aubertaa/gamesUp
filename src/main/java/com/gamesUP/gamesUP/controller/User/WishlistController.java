package com.gamesUP.gamesUP.controller.User;

import com.gamesUP.gamesUP.DTO.User.WishlistDTO;
import com.gamesUP.gamesUP.DTO.User.WishlistResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.User.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {
    @Autowired
    private WishlistService wishListService;

    @GetMapping
    public List<WishlistResponseDTO> getAllWishlists() {
        return wishListService.getAllWishlists();
    }

    @GetMapping("/{id}")
    public WishlistResponseDTO getWishlistById(@PathVariable Long id) {
        return wishListService.getWishlistById(id);
    }

    @PostMapping
    public void addWishlist(@RequestBody WishlistDTO wishListDTO) {
        try {
            wishListService.addWishlist(wishListDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("wishlist already exists for this user");
        }
    }

    @PutMapping("/{id}")
    public void updateWishlist(@PathVariable Long id, @RequestBody WishlistDTO wishListDTO) {
        wishListService.updateWishlist(id, wishListDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteWishlist(@PathVariable Long id) {
        wishListService.deleteWishlist(id);
    }
}

package com.gamesUP.gamesUP.controller.client;

import com.gamesUP.gamesUP.DTO.User.AvisDTO;
import com.gamesUP.gamesUP.DTO.User.UserDTO;
import com.gamesUP.gamesUP.DTO.User.WishlistDTO;
import com.gamesUP.gamesUP.DTO.User.WishlistResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.User.AvisService;
import com.gamesUP.gamesUP.service.User.UserService;
import com.gamesUP.gamesUP.service.User.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/users")
public class ClientUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private WishlistService wishListService;

    @Autowired
    private AvisService avisService;
    
    @PostMapping("/avis")
    public void addAvis(@RequestBody AvisDTO avisDTO) {
        avisService.addAvis(avisDTO);
    }
    
    @PutMapping("/avis/{id}")
    public void updateAvis(@PathVariable Long id, @RequestBody AvisDTO avisDTO) {
        avisService.updateAvis(id, avisDTO);
    }
    
    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
    }

    @GetMapping("/wishlists/{id}")
    public WishlistResponseDTO getWishlistById(@PathVariable Long id) {
        return wishListService.getWishlistById(id);
    }

    @PostMapping("/wishlists")
    public void addWishlist(@RequestBody WishlistDTO wishListDTO) {
        try {
            wishListService.addWishlist(wishListDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("wishlist already exists for this user");
        }
    }

    @PutMapping("/wishlists/{id}")
    public void updateWishlist(@PathVariable Long id, @RequestBody WishlistDTO wishListDTO) {
        wishListService.updateWishlist(id, wishListDTO);
    }

    @DeleteMapping("/wishlists/{id}")
    public void deleteWishlist(@PathVariable Long id) {
        wishListService.deleteWishlist(id);
    }

}

package com.gamesUP.gamesUP.controller.admin;

import com.gamesUP.gamesUP.DTO.User.AvisResponseDTO;
import com.gamesUP.gamesUP.DTO.User.UserDTO;
import com.gamesUP.gamesUP.DTO.User.UserResponseDTO;
import com.gamesUP.gamesUP.DTO.User.WishlistResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.User.AvisService;
import com.gamesUP.gamesUP.service.User.UserService;
import com.gamesUP.gamesUP.service.User.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WishlistService wishListService;

    @Autowired
    private AvisService avisService;
    
    @GetMapping("/avis")
    public List<AvisResponseDTO> getAllAvis() {
        return avisService.getAllAvis();
    }

    @GetMapping("/avis/{id}")
    public AvisResponseDTO getAvisById(@PathVariable Long id) {
        return avisService.getAvisById(id);
    }

    @DeleteMapping("/avis/{id}")
    public void deleteAvis(@PathVariable Long id) {
        avisService.deleteAvis(id);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/search/email")
    public UserResponseDTO getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/search/username")
    public UserResponseDTO getUserByUsername(@RequestParam String username) {
        return userService.getUserByName(username);
    }

    @PostMapping
    public void addUser(@RequestBody UserDTO userDTO) {
        try {
            userService.addUser(userDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("User already exists");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/wishlists")
    public List<WishlistResponseDTO> getAllWishlists() {
        return wishListService.getAllWishlists();
    }

}

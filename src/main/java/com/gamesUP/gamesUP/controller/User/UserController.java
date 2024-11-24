package com.gamesUP.gamesUP.controller.User;

import com.gamesUP.gamesUP.DTO.User.UserDTO;
import com.gamesUP.gamesUP.DTO.User.UserResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('administrateur')")
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('administrateur')")
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasRole('administrateur')")
    @GetMapping("/search/email")
    public UserResponseDTO getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @PreAuthorize("hasRole('administrateur')")
    @GetMapping("/search/username")
    public UserResponseDTO getUserByUsername(@RequestParam String username) {
        return userService.getUserByName(username);
    }

    @PreAuthorize("hasRole('administrateur')")
    @PostMapping
    public void addUser(@RequestBody UserDTO userDTO) {
        try {
            userService.addUser(userDTO);
        } catch (Exception e) {
            throw new EntityAlreadyExistException("User already exists");
        }
    }

    @PreAuthorize("hasAnyRole('administrateur', 'client')")
    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
    }

    @PreAuthorize("hasRole('administrateur')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}

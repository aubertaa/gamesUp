package com.gamesUP.gamesUP.controller.User;

import com.gamesUP.gamesUP.DTO.User.UserDTO;
import com.gamesUP.gamesUP.DTO.User.UserResponseDTO;
import com.gamesUP.gamesUP.Exceptions.EntityAlreadyExistException;
import com.gamesUP.gamesUP.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

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

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
